package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.DbSouce;
import com.smart.cityos.datav.domain.model.DbSouceModel;
import com.smart.cityos.datav.repository.DbSourceRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-19
 * @modified By beckfun
 */
@Service
public class DbSourceService implements ISourceService {

  @Autowired
  private DbSourceRepository dbSourceRepository;

  public void add(DbSouceModel dbSouceModel) {
    DbSouce dbSouce = new DbSouce();
    dbSouce.setType(dbSouceModel.getType());
    dbSouce.setAddr(dbSouceModel.getAddr());
    dbSouce.setPort(dbSouceModel.getPort());
    dbSouce.setInstance(dbSouceModel.getInstance());
    dbSouce.setCharacterSet(dbSouceModel.getCharacterSet());
    dbSouce.setName(dbSouceModel.getName());
    dbSouce.setDesc(dbSouceModel.getDesc());
    dbSouce.setLoginUsername(dbSouceModel.getLoginUsername());
    dbSouce.setLoginPassword(dbSouceModel.getLoginPassword());
    dbSouce.setSql(dbSouceModel.getSql());
    dbSourceRepository.save(dbSouce);
  }

  public void delete(String id) {
    dbSourceRepository.delete(id);
  }

  public void edit(String id, DbSouceModel dbSouceModel) {
    DbSouce dbSouce = dbSourceRepository.findOne(id);
    dbSouce.setType(dbSouceModel.getType());
    dbSouce.setAddr(dbSouceModel.getAddr());
    dbSouce.setPort(dbSouceModel.getPort());
    dbSouce.setInstance(dbSouceModel.getInstance());
    dbSouce.setCharacterSet(dbSouceModel.getCharacterSet());
    dbSouce.setName(dbSouceModel.getName());
    dbSouce.setDesc(dbSouceModel.getDesc());
    dbSouce.setLoginUsername(dbSouceModel.getLoginUsername());
    dbSouce.setLoginPassword(dbSouceModel.getLoginPassword());
    dbSouce.setSql(dbSouceModel.getSql());
    dbSourceRepository.save(dbSouce);
  }


  public Page<DbSouce> fetch(String name, Pageable pageable) {
    return name == null || name.isEmpty() ? dbSourceRepository.findAll(pageable)
        : dbSourceRepository.findAllByNameLike(name, pageable);
  }

  public DbSouce get(String id) {
    return dbSourceRepository.findOne(id);
  }

  public Connection getCon(DbSouce dbSouce) {
    String driver = "com.mysql.jdbc.Driver";
    //其中test为数据库名称
    String url =
        "jdbc:mysql://" + dbSouce.getAddr() + ":" + dbSouce.getPort() + "/" + dbSouce.getInstance();
    Connection conn = null;
    try {
      Class.forName(driver);
      conn = DriverManager
          .getConnection(url, dbSouce.getLoginUsername(), dbSouce.getLoginPassword());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return conn;
  }

  public JSONArray getSelect(DbSouce dbSouce) throws SQLException {
    Connection conn = getCon(dbSouce);
    PreparedStatement pst;
    JSONArray jsonArray = new JSONArray();
    try {
      pst = conn.prepareStatement(dbSouce.getSql());
      ResultSet rs = pst.executeQuery();
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      while (rs.next()) {
        JSONObject object = new JSONObject();
        for (int i = 1; i <= columnCount; i++) {
          String columnName=rsmd.getColumnName(i);
          Object value=rs.getObject(i);
          object.put(columnName, value);
        }
        jsonArray.add(object);
      }
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      conn.close();
    }
    return jsonArray;
  }

  @Override
  public String getType() {
    return "Db";
  }

  @Override
  public Object getContent(String id) {
    DbSouce dbSouce = get(id);
    Object object = null;
    try {
      object = getSelect(dbSouce);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (object!=null&& dbSouce.getTransScript() != null && dbSouce.getTransScript().getFormatMessage() != null
        && !dbSouce.getTransScript().getFormatMessage().isEmpty()) {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine engine = manager.getEngineByName("JavaScript");

      engine.put("o", object);
      try {
        String javaScript =
            "var t=[];" + dbSouce.getTransScript().getTranslatorScriptContent() + "t;";
        Object result = engine.eval(javaScript);

        JSONObject jsonObject = JSONObject.fromObject(result);
        Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);
        object = judge(map);

      } catch (ScriptException e) {
        e.printStackTrace();
      }
    }
    JSONObject result = new JSONObject();
    result.put("code", 20000);
    result.put("data", object);
    return result;

  }
  private Object judge(Map<String, Object> map) {
    List<String> keys = map.keySet().stream().filter(x -> isNumber(x)).sorted()
        .collect(Collectors.toList());
    if (keys.size() == map.keySet().size() && isOrderNumeric(String.join("", keys))) {
      JSONArray jsonArray = new JSONArray();
      for (String key : keys
      ) {
        Object object = map.get(key);
        Map<String, Object> temp = (Map<String, Object>) JSONObject
            .toBean(JSONObject.fromObject(object), Map.class);
        jsonArray.add(judge(temp));
      }
      return jsonArray;
    } else {
      JSONObject jsonObject = new JSONObject();
      for (String key : map.keySet()) {

        Object value = map.get(key);
        if (value instanceof MorphDynaBean) {
          Map<String, Object> temp = (Map<String, Object>) JSONObject
              .toBean(JSONObject.fromObject(map.get(key)), Map.class);
          jsonObject.put(key, judge(temp));
        } else {
          jsonObject.put(key, value);
        }
      }
      return jsonObject;
    }
  }


  public boolean isNumber(String str) {
    String reg = "^[0-9]+(.[0-9]+)?$";
    return str.matches(reg);
  }

  public boolean isOrderNumeric(String numOrStr) {
    boolean flag = true;
    for (int i = 0; i < numOrStr.length(); i++) {
      if (i > 0) {
        // 判断如123456
        int num = Integer.parseInt(numOrStr.charAt(i) + "");
        int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") + 1;
        if (num != num_) {
          flag = false;
          break;
        }
      }
    }
    if (!flag) {
      for (int i = 0; i < numOrStr.length(); i++) {
        if (i > 0) {
          // 判断如654321
          int num = Integer.parseInt(numOrStr.charAt(i) + "");
          int num_ = Integer.parseInt(numOrStr.charAt(i - 1) + "") - 1;
          if (num != num_) {
            flag = false;
            break;
          }
        }
      }
    }
    return flag;
  }
}

package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.ApiSource;
import com.smart.cityos.datav.domain.KeyValue;
import com.smart.cityos.datav.domain.RestfulConfig;
import com.smart.cityos.datav.domain.model.ApiSourceModel;
import com.smart.cityos.datav.repository.ApiSourceRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
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
public class ApiSourceService implements ISourceService {

  @Autowired
  private ApiSourceRepository apiSourceRepository;

  public Object remoteRESTfulApi(RestfulConfig restfulConfig) {
    String result = "";

    StringBuffer url = new StringBuffer(restfulConfig.getUrl());
    List<KeyValue> paramers = restfulConfig.getQueryParams();
    if (paramers != null && paramers.size() > 0) {
      int index = url.indexOf("?");
      if (index == -1) {
        url.append("?");
      } else if ((index + 1) < url.length()) {
        url.append("&");
      }
      String[] paramsArray = new String[paramers.size()];
      int arrayIndex = 0;
      for (KeyValue parameter : paramers) {
        paramsArray[arrayIndex++] = parameter.getKey() + "=" + (parameter.getValue() == null ? ""
            : parameter.getValue());
      }
      url.append(String.join("&", paramsArray));
    }
    try {
      HttpURLConnection uConnection = createConnection(restfulConfig.getMethod(),
          url.toString());
      uConnection.setConnectTimeout(10 * 1000);
      for (KeyValue parameter : restfulConfig.getHeaders()) {

        uConnection.setRequestProperty(parameter.getKey(),
            (parameter.getValue() == null ? "" : parameter.getValue()));
      }

      result = getResponseResult(uConnection, restfulConfig.getBody());

    } catch (Exception ex) {
    }
    return result;
  }

  protected HttpURLConnection createConnection(String formMethod, String url)
      throws IOException {
    URL targetUrl = new URL(url);
    HttpURLConnection httpConnection = (HttpURLConnection) targetUrl
        .openConnection();

    httpConnection.setDoOutput(true);
    httpConnection.setDoInput(true);
    // 连接超时 单位毫秒
    httpConnection.setConnectTimeout(30000);
    // 读取超时
    httpConnection.setReadTimeout(30000);
    httpConnection.setRequestMethod(formMethod.toUpperCase());
    httpConnection.setRequestProperty("Accept",
        "*/*");

    return httpConnection;
  }


  /**
   * 获取服务器输出内容
   */
  protected String getResponseResult(HttpURLConnection httpConnection,
      String input) throws IOException {
    // 当不需要传递参数内容的时候，无需执行该步骤
    if (input != null && !input.trim().isEmpty()) {
      OutputStream outputStream = httpConnection.getOutputStream();
      outputStream.write(input.getBytes("UTF-8"));
      outputStream.flush();
    }
    String result;
    try {
      int responseCode = httpConnection.getResponseCode();
      switch (responseCode) {
        // 状态码200标识执行正常，状态码201表示，该内容已经被创见
        case 200:
        case 201:
          BufferedReader responseBuffer1 = new BufferedReader(
              new InputStreamReader(httpConnection.getInputStream(),
                  "UTF-8"));
          result = responseBuffer1.readLine();
          break;
        default: {
          BufferedReader responseBuffer2 = new BufferedReader(
              new InputStreamReader(httpConnection.getErrorStream(),
                  "UTF-8"));
          result = responseBuffer2.readLine();
          throw new RuntimeException("Failed : HTTP error code : "
              + httpConnection.getResponseCode() + "; message : "
              + result);
        }
      }
    } catch (Exception ex) {
      System.out.println(input);
      throw ex;
    }
    return result;
  }


  public void add(ApiSourceModel apiSourceModel) {
    ApiSource apiSource = new ApiSource();
    apiSource.setName(apiSourceModel.getName());
    apiSource.setDesc(apiSourceModel.getDesc());
    apiSource.setApiParam(apiSourceModel.getApiParam());
    apiSource.setTransScript(apiSourceModel.getTransScript());
    apiSourceRepository.save(apiSource);
  }

  public void delete(String id) {
    apiSourceRepository.delete(id);
  }

  public void edit(String id, ApiSourceModel apiSourceModel) {
    ApiSource apiSource = apiSourceRepository.findOne(id);
    apiSource.setName(apiSourceModel.getName());
    apiSource.setDesc(apiSourceModel.getDesc());
    apiSource.setApiParam(apiSourceModel.getApiParam());
    apiSource.setTransScript(apiSourceModel.getTransScript());
    apiSourceRepository.save(apiSource);
  }

  public Page<ApiSource> fetch(String name, Pageable pageable) {
    return name == null || name.isEmpty() ? apiSourceRepository.findAll(pageable)
        : apiSourceRepository.findAllByNameLike(name, pageable);
  }

  @Override
  public String getType() {
    return "Api";
  }

  @Override
  public Object getContent(String id) {
    ApiSource apiSource = get(id);
    RestfulConfig restfulConfig = apiSource.getApiParam();
    Object object = remoteRESTfulApi(restfulConfig);

    if (apiSource.getTransScript() != null && apiSource.getTransScript().getFormatMessage() != null
        && !apiSource.getTransScript().getFormatMessage().isEmpty()) {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine engine = manager.getEngineByName("JavaScript");

      engine.put("o", object);

      try {
        String javaScript =
            "var t={};" + apiSource.getTransScript().getTranslatorScriptContent() + "t;";
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
  public ApiSource get(String id) {
    return apiSourceRepository.findOne(id);
  }
}

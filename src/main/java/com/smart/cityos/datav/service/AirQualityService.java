package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.DbInfo;
import com.smart.cityos.datav.domain.ExecuteQueryParam;
import com.smart.cityos.datav.domain.Result;
import com.smart.cityos.datav.domain.model.AQI7days;
import com.smart.cityos.datav.domain.model.AQITrend;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.smart.cityos.datav.service.feign.config.ConfigFeignService;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 空气质量业务类
 *
 * @author: mingcheong
 * @date Created in 2018-08-27
 */
@Slf4j
@Service
public class AirQualityService {

    @Autowired
    private ConfigFeignService configFeignService;

    /**
     * 获取12小时AQI趋势数据列表
     *
     * @return 12小时AQI趋势模型集合
     */
    public List<AQITrend> get12HourAQITrend(String id) {

        List<AQITrend> list = new ArrayList<AQITrend>();
        if (id.equals("0")) {
            list.add(new AQITrend("01时", 45));
            list.add(new AQITrend("02时", 55));
            list.add(new AQITrend("03时", 66));
            list.add(new AQITrend("04时", 77));
            list.add(new AQITrend("05时", 88));
            list.add(new AQITrend("06时", 33));
            list.add(new AQITrend("07时", 44));
            list.add(new AQITrend("08时", 66));
            list.add(new AQITrend("09时", 88));
            list.add(new AQITrend("10时", 11));
            list.add(new AQITrend("11时", 98));
            list.add(new AQITrend("12时", 110));
        } else {
            list.add(new AQITrend("01时", 24));
            list.add(new AQITrend("02时", 45));
            list.add(new AQITrend("03时", 67));
            list.add(new AQITrend("04时", 23));
            list.add(new AQITrend("05时", 45));
            list.add(new AQITrend("06时", 102));
            list.add(new AQITrend("07时", 34));
            list.add(new AQITrend("08时", 54));
            list.add(new AQITrend("09时", 56));
            list.add(new AQITrend("10时", 15));
            list.add(new AQITrend("11时", 73));
            list.add(new AQITrend("12时", 74));
        }
        return list;
    }


    /**
     * 获取7天AQI柱状图数据列表
     *
     * @return AQI柱状图数据列表
     */
    public List<AQI7days> get7DaysAQITrend(String id) {
        List<AQI7days> list = new ArrayList<>();
        if (id.equals("0")) {
            list.add(new AQI7days("08/18", 2, "1"));
            list.add(new AQI7days("08/18", 20, "2"));
            list.add(new AQI7days("08/18", 30, "3"));
            list.add(new AQI7days("08/19", 90, "1"));
            list.add(new AQI7days("08/19", 1, "2"));
            list.add(new AQI7days("08/19", 20, "3"));
            list.add(new AQI7days("08/20", 22, "1"));
            list.add(new AQI7days("08/20", 10, "2"));
            list.add(new AQI7days("08/20", 30, "3"));
            list.add(new AQI7days("08/21", 20, "1"));
            list.add(new AQI7days("08/21", 20, "2"));
            list.add(new AQI7days("08/21", 0, "3"));
            list.add(new AQI7days("08/22", 60, "1"));
            list.add(new AQI7days("08/22", 20, "2"));
            list.add(new AQI7days("08/22", 10, "3"));
            list.add(new AQI7days("08/23", 0, "1"));
            list.add(new AQI7days("08/23", 29, "2"));
            list.add(new AQI7days("08/23", 32, "3"));
            list.add(new AQI7days("08/24", 22, "1"));
            list.add(new AQI7days("08/24", 65, "2"));
            list.add(new AQI7days("08/24", 10, "3"));
        } else {
            list.add(new AQI7days("08/18", 24, "1"));
            list.add(new AQI7days("08/18", 0, "2"));
            list.add(new AQI7days("08/18", 0, "3"));
            list.add(new AQI7days("08/19", 40, "1"));
            list.add(new AQI7days("08/19", 0, "2"));
            list.add(new AQI7days("08/19", 0, "3"));
            list.add(new AQI7days("08/20", 36, "1"));
            list.add(new AQI7days("08/20", 0, "2"));
            list.add(new AQI7days("08/20", 0, "3"));
            list.add(new AQI7days("08/21", 0, "1"));
            list.add(new AQI7days("08/21", 70, "2"));
            list.add(new AQI7days("08/21", 0, "3"));
            list.add(new AQI7days("08/22", 90, "1"));
            list.add(new AQI7days("08/22", 0, "2"));
            list.add(new AQI7days("08/22", 0, "3"));
            list.add(new AQI7days("08/23", 49, "1"));
            list.add(new AQI7days("08/23", 0, "2"));
            list.add(new AQI7days("08/23", 0, "3"));
            list.add(new AQI7days("08/24", 0, "1"));
            list.add(new AQI7days("08/24", 0, "2"));
            list.add(new AQI7days("08/24", 101, "3"));
        }
        return list;
    }


    /**
     * 根据日历获取降雨量数据列表
     *
     * @return 降雨量数据列表
     */
    public List<String[]> getCalendarAQl(String id, String date) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sDateFormat.parse(date);
            log.debug("执行日期格式化 : {}", date);
        } catch (ParseException px) {
            d = new Date();
            log.error("日期格式错误", px);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date begin = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = cal.getTime();

        List<String[]> result = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        while (begin.getTime() <= end.getTime()) {
            result.add(new String[]{dFormat.format(tempStart.getTime()), "0"});
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }

        DbInfo db=new DbInfo();
        db.setCharacterSet("UTF8");
        db.setDbInstanceName("ldb_5ac2df0ca4600913f8beeb17");
        db.setDbIp("192.168.10.11");
        db.setDbPassword("cityos@2017");
        db.setDbPort("31631");
        db.setDbType("MYSQL");
        db.setDbUser("cityos");
        ExecuteQueryParam eqp=new ExecuteQueryParam(db,"select * from EC_Day_1532332075029 where stationId=0 and DATE_FORMAT(SystemDateTime,'%Y-%m-%d')>='2017-10-1'  and DATE_FORMAT(SystemDateTime,'%Y-%m-%d')<= '2017-11-29' order by systemDateTime desc");

        Result re=configFeignService.executeQuery(eqp);

        Integer[] month7 = new Integer[]{
                30, 40, 40, 50, 50, 50, 50, 90, 90, 10,
                30, 100, 100, 100, 40, 40, 40, 40, 40, 40,
                20, 20, 20, 20, 20, 20, 20, 50, 50, 20,
                50
        };
        Integer[] month8 = new Integer[]{
                30, 100, 100, 100, 40, 40, 40, 40, 40, 40,
                20, 20, 20, 20, 20, 20, 20, 50, 50, 51,
                30, 40, 40, 50, 50, 50, 50, 90, 90, 0,
                0
        };

        for (int i = 0; i < result.size(); i++) {
            if (begin.getMonth() == 8) {
                result.get(i)[1] = String.valueOf(month8[i]);
            } else {
                result.get(i)[1] = String.valueOf(month7[i]);
            }
        }
        return result;
    }

    /**
     *获取天气信息
     *
     * @author shenhj
     * @date 2018/9/3 11:08
     */
    public List<String> getWeathers(){
        Document doc = null;
        HttpURLConnection uConnection = null;
        List<String> result = new ArrayList<>();
        try {
            //创建url链接
            uConnection = createConnection("http://www.webxml.com.cn/WebServices/WeatherWebService.asmx/getWeatherbyCityName?theCityName=宁波");
            //获取接口输出内容
            String xml = getResponseResult(uConnection);
            result = readStringXmlOut(xml);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            uConnection.disconnect();
        }

        return result;
    }

    public String getResponseResult(HttpURLConnection httpConnection)
        throws IOException {
        String result = "";
        try {
            int responseCode = httpConnection.getResponseCode();
            switch (responseCode) {
                // 状态码200标识执行正常，状态码201表示，该内容已经被创见
                case 200:
                case 201:
                    BufferedReader responseBuffer1 = new BufferedReader(
                        new InputStreamReader(httpConnection.getInputStream(),
                            "UTF-8"));
                    String line = "";
                    while((line = responseBuffer1.readLine()) != null) {
                        result += line;
                    }
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
            throw ex;
        }
        return result;
    }

    public HttpURLConnection createConnection(String url)
        throws IOException {
        URL targetUrl = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
        httpConnection.setDoOutput(true);
        httpConnection.setDoInput(true);
        // 连接超时 单位毫秒
        httpConnection.setConnectTimeout(30000);
        // 读取超时
        httpConnection.setReadTimeout(30000);
        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("Accept", "*/*");
        return httpConnection;
    }

  public static List<String> readStringXmlOut(String xml) {
    List<String> list = new ArrayList();
    Document doc = null;
    try {
      // 将字符串转为XML
      doc = DocumentHelper.parseText(xml);
      // 获取根节点
      Element rootElt = doc.getRootElement();
      // 拿到根节点的名称
      System.out.println("根节点：" + rootElt.getName());

      Iterator<Element> iter = rootElt.elementIterator();
      // 遍历节点
      while (iter.hasNext()) {
        Element recordEle = (Element) iter.next();
        list.add(recordEle.getText());
      }
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }

}
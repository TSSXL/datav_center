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
import java.lang.reflect.Array;
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
    public List<AQITrend> get12HourAQITrend(Map data) {


        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH");

        List<AQITrend> list = new ArrayList<AQITrend>();


        String sql = "select * from " + data.get("tableName")
                + " where stationId=" + data.get("id") + " order by TimePoint desc limit 0,12";
        Map query = new HashMap();
        query.put("dbInfo", data.get("dbInfo"));
        query.put("sql", sql);

        List<Map> re = configFeignService.executeQuery(query);


        for (int i = 0; i < re.size(); i++) {
            Date d = null;
            try {
                d = dFormat.parse(String.valueOf(re.get(i).get("TimePoint")));
                //log.debug("执行日期格式化 : {}", re.get(i).get("TimePoint"));
            } catch (ParseException px) {
                d = new Date();
                log.error("日期格式错误", px);
            }
            list.add(0, new AQITrend(sDateFormat.format(d) + "时", Integer.parseInt(String.valueOf(re.get(i).get("AQI")))));
        }

        return list;
    }


    /**
     * 获取7天AQI柱状图数据列表
     *
     * @return AQI柱状图数据列表
     */
    public List<AQI7days> get7DaysAQITrend(Map data) {
        List<AQI7days> list = new ArrayList<>();
        String id = "0";
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

        String sql = String.format("select  * from %s where  stationId=%s  and date_sub(curdate(), INTERVAL 7 DAY) <= date(`TimePoint`)", data.get("tableName"), data.get("stationId"));
        Map<String, Object> query = new HashMap<>();
        query.put("dbInfo", data.get("dbInfo"));
        query.put("sql", sql);
        List<Map> result = configFeignService.executeQuery(query);
        return list;
    }


    /**
     * 根据日历获取降雨量数据列表
     *
     * @return 降雨量数据列表
     */
    public List<String[]> getCalendarAQl(Map data) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        List<String[]> result = new ArrayList<>();
        String range = (String) data.get("range");
        try {
            d = sDateFormat.parse(range);
            log.debug("执行日期格式化 : {}", range);
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


        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        while (begin.getTime() <= end.getTime()) {
            result.add(new String[]{dFormat.format(tempStart.getTime()), "0"});
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }

        String sql = "select DATE_FORMAT(TimePoint ,'%Y-%m-%d') date,AQI from " + data.get("tableName") + " where stationId=" + data.get("stationId") + " and " +
                "DATE_FORMAT(TimePoint ,'%Y-%m-%d')>='" + result.get(0)[0].toString() + "'  " +
                "and DATE_FORMAT(TimePoint ,'%Y-%m-%d')<= '" + result.get(result.size() - 1)[0].toString() + "' order by TimePoint  desc";


        Map query = new HashMap();
        query.put("dbInfo", data.get("dbInfo"));
        query.put("sql", sql);

        List<Map> re = configFeignService.executeQuery(query);

        for (int i = 0; i < result.size(); i++) {
            for (int k = 0; k < re.size(); k++) {
                if (result.get(i)[0].equals(re.get(k).get("date"))) {
                    result.get(i)[1] = String.valueOf(re.get(k).get("AQI"));
                }
            }

        }
        return result;
    }


    /**
     * 获取空气质量站点详情信息
     *
     * @param data
     * @return
     */
    public List<Map> getStationInfoList(Map data){

        //查询所有站点信息
        String stationSql="select * from "+data.get("tableName")+" "+data.get("where");

        Map stationQuery=new HashMap();
        stationQuery.put("dbInfo",data.get("dbInfo"));
        stationQuery.put("sql",stationSql);

        List<Map> stations=configFeignService.executeQuery(stationQuery);

        //根据站点条数获取最新的实时信息
        String statusSql="select h.*,DATE_FORMAT(h.TimePoint ,'%Y-%m-%d %H:%i:%s') formatTime from "
                +data.get("hourTableName")+" h "+data.get("where")+" order by TimePoint desc limit 0,"+stations.size();

        Map statusQuery=new HashMap();
        statusQuery.put("dbInfo",data.get("dbInfo"));
        statusQuery.put("sql",statusSql);

        List<Map> status=configFeignService.executeQuery(statusQuery);

        //将站点信息和实时信息合并
        List<Map> result=returnStationStatusList(stations,status);


        return result;
    }

    public List<Map> returnStationStatusList(List<Map> stations,List<Map> status){
        List<Map> result=new ArrayList<Map>();
        for(int i=0;i<stations.size();i++){
            Map station=stations.get(i);
            for(int k=0;k<status.size();k++){
                Map ss=status.get(k);
                //当站点ID相同时保存
                if(station.get("StationID").equals(ss.get("StationID"))){
                    station.put("AQI",ss.get("AQI"));
                    station.put("formatTime",ss.get("formatTime"));
                    station.put("PrimaryPollutant",ss.get("PrimaryPollutant"));
                    int aqi=Integer.parseInt(String.valueOf(ss.get("AQI")));
                    if(aqi<50){
                        station.put("color","#6cd888");
                        station.put("status","优");
                    }else if(aqi>=50 && aqi<100){
                        station.put("color","#fff280");
                        station.put("status","良");
                    }else if(aqi>=100){
                        station.put("color","#ff688f");
                        station.put("status","轻度污染");
                    }
                    result.add(station);

                }
            }

        }

        return result;
    }

    /**
     * 获取天气信息
     *
     * @author shenhj
     * @date 2018/9/3 11:08
     */
    public List<String> getWeathers() {
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
        } finally {
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
                    while ((line = responseBuffer1.readLine()) != null) {
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
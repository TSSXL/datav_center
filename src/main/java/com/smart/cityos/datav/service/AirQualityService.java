package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.model.AQI7days;
import com.smart.cityos.datav.domain.model.AQITrend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.smart.cityos.datav.service.feign.config.ConfigFeignService;
import com.smart.cityos.datav.utils.DateTimeUtils;
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
        String sql = String.format("select  * from %s where  stationId=%s order by TimePoint  desc limit 7", data.get("tableName"), data.get("id"));
        Map<String, Object> query = new HashMap<>();
        query.put("dbInfo", data.get("dbInfo"));
        query.put("sql", sql);
        List<Map> result = configFeignService.executeQuery(query);
        result.forEach(v -> {
            int aqi = Integer.parseInt(v.get("AQI").toString());
            String dateStr = null;
            try {
                dateStr = DateTimeUtils.toString(DateTimeUtils.toDate(v.get("TimePoint").toString()), "MM/dd");
            } catch (ParseException e) {
                log.error("日期格式转换错误", e);
            }
            if (aqi < 50) {
                list.add(0,new AQI7days(dateStr, 0, "3"));
                list.add(0,new AQI7days(dateStr, 0, "2"));
                list.add(0,new AQI7days(dateStr, aqi, "1"));

            } else if (aqi >= 50 && aqi<100) {
                list.add(0,new AQI7days(dateStr, 0, "3"));
                list.add(0,new AQI7days(dateStr, aqi, "2"));
                list.add(0,new AQI7days(dateStr, 0, "1"));
            } else if (aqi >= 100) {
                list.add(0,new AQI7days(dateStr, aqi, "3"));
                list.add(0,new AQI7days(dateStr, 0, "2"));
                list.add(0,new AQI7days(dateStr, 0, "1"));

            }
        });
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

    /**
     * 合并站点列表状态
     * @param stations
     * @param status
     * @return
     */
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
                    station.put("PrimaryPollutant",ss.get("PrimaryPollutant")==null?'-':ss.get("PrimaryPollutant"));
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
     * 获取站点地图信息
     * @param data
     * @return
     */
    public List<Map> getStationInfoMap(Map data){

        //查询所有站点信息
        String stationSql="select * from "+data.get("tableName")+" "+data.get("where");

        Map stationQuery=new HashMap();
        stationQuery.put("dbInfo",data.get("dbInfo"));
        stationQuery.put("sql",stationSql);

        List<Map> stations=configFeignService.executeQuery(stationQuery);

        //根据站点条数获取最新的实时信息
        String statusSql="select h.*,DATE_FORMAT(h.TimePoint ,'%Y-%m-%d %H:%i:%s') formatTime from "
                +data.get("hourTableName")+" h "+data.get("where")+" order by TimePoint desc limit 0,24";

        Map statusQuery=new HashMap();
        statusQuery.put("dbInfo",data.get("dbInfo"));
        statusQuery.put("sql",statusSql);

        List<Map> status=configFeignService.executeQuery(statusQuery);

        //将站点信息和实时信息合并
        List<Map> result=returnStationStatusMap(stations,status);


        return result;
    }


    /**
     * 合并站点状态地图
     * @param stations
     * @param status
     * @return
     */
    public List<Map> returnStationStatusMap(List<Map> stations,List<Map> status){
        List<Map> result=new ArrayList<Map>();
        for(int i=0;i<stations.size();i++){
            Map station=stations.get(i);
            for(int k=0;k<status.size();k++){
                Map ss=status.get(k);
                //当站点ID相同时保存
                if(station.get("StationID").equals(ss.get("StationID"))){
                    Map map=new HashMap();
                    Object[] x={station.get("Longitude"),station.get("Latitude"),ss.get("AQI")};
                    map.put("name",station.get("PositionName"));
                    //map.put("id",station.get("StationID"));
                    map.put("value",x);
                    int aqi=Integer.parseInt(String.valueOf(ss.get("AQI")));
                    Map nomal=new HashMap();
                    Map color=new HashMap();
                    if(aqi<50){
                        color.put("color","#6cd888");
                    }else if(aqi>=50 && aqi<100){
                        color.put("color","#fff280");
                    }else if(aqi>=100){
                        color.put("color","#ff688f");
                    }
                    nomal.put("normal",color);
                    map.put("itemStyle",nomal);
                    result.add(map);

                }
            }

        }

        return result;
    }


    /**
     * 获取站点信息
     * @param data
     * @return
     */
    public List<Map> getStationInfo(Map data){

        //查询所有站点信息
        String stationSql="select * from "+data.get("tableName")+" "+data.get("where");

        Map stationQuery=new HashMap();
        stationQuery.put("dbInfo",data.get("dbInfo"));
        stationQuery.put("sql",stationSql);

        List<Map> stations=configFeignService.executeQuery(stationQuery);

        //根据站点条数获取最新的实时信息
        String statusSql="select h.*,DATE_FORMAT(h.TimePoint ,'%Y-%m-%d %H:%i:%s') formatTime from "
                +data.get("hourTableName")+" h  order by TimePoint desc limit 0,24";

        Map statusQuery=new HashMap();
        statusQuery.put("dbInfo",data.get("dbInfo"));
        statusQuery.put("sql",statusSql);

        List<Map> status=configFeignService.executeQuery(statusQuery);

        //将站点信息和实时信息合并
        List<Map> stationInfo=returnStationInfoMap(stations,status);

        List<Map> result=new ArrayList<Map>();
        Map map=new HashMap();
        map.put("areaName",data.get("areaName"));
        map.put("stationList",stationInfo);
        result.add(map);

        return result;
    }

    /**
     * 合并站点信息
     * @param stations
     * @param status
     * @return
     */
    public List<Map> returnStationInfoMap(List<Map> stations,List<Map> status){
        List<Map> result=new ArrayList<Map>();
        for(int i=0;i<stations.size();i++){
            Map station=stations.get(i);
            for(int k=0;k<status.size();k++){
                Map ss=status.get(k);
                //当站点ID相同时保存
                if(station.get("StationID").equals(ss.get("StationID"))){
                    Map map=new HashMap();
                    map.put("AQI",ss.get("AQI"));
                    map.put("name",station.get("PositionName"));
                    map.put("formatTime",ss.get("formatTime"));
                    int aqi=Integer.parseInt(String.valueOf(ss.get("AQI")));
                    if(aqi<50){
                        map.put("AQIStyle","color:#6cd888;font-size:25px;");
                    }else if(aqi>=50 && aqi<100){
                        map.put("AQIStyle","color:#fff280;font-size:25px;");
                    }else if(aqi>=100){
                        map.put("AQIStyle","color:#ff688f;font-size:25px;");
                    }
                    result.add(map);

                }
            }

        }

        return result;
    }

    /**
     * 获取全市指标信息
     * @param data
     * @return
     */
    public List<Map> getStationTargetInfo(Map data){


        //根据站点条数获取最新的实时信息
        String sql="select h.*,DATE_FORMAT(h.TimePoint ,'%Y-%m-%d %H:%i:%s') formatTime from "
                +data.get("tableName")+" h where StationID=0 order by TimePoint desc limit 0,1";

        Map nowQuery=new HashMap();
        nowQuery.put("dbInfo",data.get("dbInfo"));
        nowQuery.put("sql",sql);
        System.out.println(sql);
        //获取最新记录
        List<Map> nowTarget=configFeignService.executeQuery(nowQuery);
        //最新记录
        String time=String.valueOf(nowTarget.get(0).get("formatTime"));
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt=null;
        try {
            dt=dFormat.parse(time);
        }catch (ParseException px){
            dt=new Date();
        }
        System.out.println(dt);
        calendar.setTime(dt);
        calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR) - 12);
        String halfTime=dFormat.format(calendar.getTime());
        calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR) - 12);
        String dayTime=dFormat.format(calendar.getTime());
        //根据站点条数获取最新的12小时前信息
        String halfSql="select h.*,DATE_FORMAT(h.TimePoint ,'%Y-%m-%d %H:%i:%s') formatTime from "
                +data.get("tableName")+" h where StationID=0 and DATE_FORMAT(h.TimePoint ,'%Y-%m-%d %H:%i:%s')='"+
                halfTime+"' order by h.TimePoint desc limit 0,1";

        Map halfQuery=new HashMap();
        halfQuery.put("dbInfo",data.get("dbInfo"));
        halfQuery.put("sql",halfSql);

        List<Map> halfTarget=configFeignService.executeQuery(halfQuery);

        //根据站点条数获取最新的24小时前信息
        String daySql="select h.*,DATE_FORMAT(h.TimePoint ,'%Y-%m-%d %H:%i:%s') formatTime from "
                +data.get("tableName")+" h where StationID=0 and DATE_FORMAT(h.TimePoint ,'%Y-%m-%d %H:%i:%s')='"+
                dayTime+"' order by h.TimePoint desc limit 0,1";

        Map dayQuery=new HashMap();
        dayQuery.put("dbInfo",data.get("dbInfo"));
        dayQuery.put("sql",daySql);

        List<Map> dayTarget=configFeignService.executeQuery(dayQuery);

        Map nowMap=new HashMap();
        nowMap.put("PM25",Float.parseFloat(String.valueOf(nowTarget.get(0).get("PM25_1H")))*1000);
        nowMap.put("SO2",Float.parseFloat(String.valueOf(nowTarget.get(0).get("SO2_1H")))*1000);
        nowMap.put("O3",Float.parseFloat(String.valueOf(nowTarget.get(0).get("O3_1H")))*1000);
        nowMap.put("PM10",Float.parseFloat(String.valueOf(nowTarget.get(0).get("PM10_1H")))*1000);
        nowMap.put("NO2",Float.parseFloat(String.valueOf(nowTarget.get(0).get("NO2_1H")))*1000);
        nowMap.put("CO",Float.parseFloat(String.valueOf(nowTarget.get(0).get("CO_1H"))));
        nowMap.put("formatTime",nowTarget.get(0).get("formatTime"));

        Map halfMap=new HashMap();
        halfMap.put("PM25",Float.parseFloat(String.valueOf(halfTarget.get(0).get("PM25_1H")))*1000);
        halfMap.put("SO2",Float.parseFloat(String.valueOf(halfTarget.get(0).get("SO2_1H")))*1000);
        halfMap.put("O3",Float.parseFloat(String.valueOf(halfTarget.get(0).get("O3_1H")))*1000);
        halfMap.put("PM10",Float.parseFloat(String.valueOf(halfTarget.get(0).get("PM10_1H")))*1000);
        halfMap.put("NO2",Float.parseFloat(String.valueOf(halfTarget.get(0).get("NO2_1H")))*1000);
        halfMap.put("CO",Float.parseFloat(String.valueOf(halfTarget.get(0).get("CO_1H"))));
        halfMap.put("formatTime",halfTarget.get(0).get("formatTime"));

        Map dayMap=new HashMap();
        dayMap.put("PM25",Float.parseFloat(String.valueOf(dayTarget.get(0).get("PM25_1H")))*1000);
        dayMap.put("SO2",Float.parseFloat(String.valueOf(dayTarget.get(0).get("SO2_1H")))*1000);
        dayMap.put("O3",Float.parseFloat(String.valueOf(dayTarget.get(0).get("O3_1H")))*1000);
        dayMap.put("PM10",Float.parseFloat(String.valueOf(dayTarget.get(0).get("PM10_1H")))*1000);
        dayMap.put("NO2",Float.parseFloat(String.valueOf(dayTarget.get(0).get("NO2_1H")))*1000);
        dayMap.put("CO",Float.parseFloat(String.valueOf(dayTarget.get(0).get("CO_1H"))));
        dayMap.put("formatTime",dayTarget.get(0).get("formatTime"));


        List<Map> result=new ArrayList<Map>();
        result.add(nowMap);
        result.add(halfMap);
        result.add(dayMap);


        return result;
    }


    /**
     * 获取最新全市信息
     * @param data
     * @return
     */
    public List<Map> getCityStationInfo(Map data){


        //根据站点条数获取最新的实时信息
        String sql="select h.*,DATE_FORMAT(h.TimePoint ,'%Y-%m-%d %H:%i:%s') formatTime from "
                +data.get("tableName")+" h where StationID=0 order by TimePoint desc limit 0,1";

        Map nowQuery=new HashMap();
        nowQuery.put("dbInfo",data.get("dbInfo"));
        nowQuery.put("sql",sql);
        //获取最新记录
        List<Map> newInfo=configFeignService.executeQuery(nowQuery);
        int aqi=Integer.parseInt(String.valueOf(newInfo.get(0).get("AQI")));

        Map map=new HashMap();
        map.put("aqiValue",aqi);
        map.put("main",newInfo.get(0).get("PrimaryPollutant")==null?'-':newInfo.get(0).get("PrimaryPollutant"));
        if(aqi<50){
            map.put("status","优");
        }else if(aqi>=50 && aqi<100){
            map.put("status","优");
        }else if(aqi>=100){
            map.put("status","轻度污染");
        }

        List<Map> result=new ArrayList<Map>();
        result.add(map);

        return result;
    }


    /**
     * 获取最新更新时间点信息---已全市平均为准
     * @param data
     * @return
     */
    public List<Map> getTimePointInfo(Map data){


        //根据站点条数获取最新的实时信息
        String sql="select DATE_FORMAT(h.TimePoint ,'%Y-%m-%d %H:%i:%s') formatTime from "
                +data.get("tableName")+" h where StationID=0 order by TimePoint desc limit 0,1";

        Map nowQuery=new HashMap();
        nowQuery.put("dbInfo",data.get("dbInfo"));
        nowQuery.put("sql",sql);
        //获取最新记录
        List<Map> newInfo=configFeignService.executeQuery(nowQuery);

        Map map=new HashMap();
        map.put("value","更新时间:"+newInfo.get(0).get("formatTime"));
        List<Map> result=new ArrayList<Map>();
        result.add(map);

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
package com.smart.cityos.datav.service;

import com.smart.cityos.datav.service.feign.config.MonitorFeignService;
import com.smart.cityos.datav.service.feign.config.ServerFeignService;
import com.smart.cityos.datav.utils.Ping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 监控平台类
 *
 * @author: mingcheong
 * @date Created in 2018-08-27
 */
@Slf4j
@Service
public class MonitorService {

    @Autowired
    private MonitorFeignService monitorFeignService;
    @Autowired
    private ServerFeignService serverFeignService;
    private final String API_CONNENT_ERROR = "api连接异常";

    /**
     * 获取采集、共享任务状态
     *
     * @return
     */
    public List<Map> getTaskStatus(Map data) {

        List<Map> result=new ArrayList<Map>();

        //获取最新记录
        List<Map> re=monitorFeignService.getTaskStatus(data);
        if(re.size()>0){
            re.forEach(r->{
                Map map=new HashMap();
                map.put("value",r.get("count"));
                map.put("url","");
                result.add(map);
            });
        }else{
            Map map=new HashMap();
            map.put("value",0);
            map.put("url","");
            result.add(map);
        }


        return result;
    }

    /**
     * 获取监控采集订阅数据量
     *
     * @return
     */
    public List<Map> getTaskDataCount(Map data) {


        //获取最新记录
        List<Map> re=monitorFeignService.getTaskDataNodesInfo(data);


        return re;
    }

    /**
     * 获取监控采集订阅异常量
     *
     * @return
     */
    public List<Map> getTaskErrorCount(Map data) {


        //获取最新记录
        List<Map> re=monitorFeignService.getTaskErrorCount(data);


        return re;
    }

    /**
     * 获取采集、订阅中心数据量显示详情
     *
     * @return
     */
    public List<Map> getTaskDataInfoCount(Map data) {


        //获取最新记录
        List<Map> re=monitorFeignService.getTaskDataInfoCount(data);


        return re;
    }

    /**
     * 获取数据库状态
     *
     * @return
     */
    public List<Map> getDbStatus(Map data) {
        //获取最新记录
        List<Map> re=monitorFeignService.getDbStatus(data);


        return re;
    }

    /**
     * 根据类型获取监控状态
     *
     * @return
     */
    public List<Map> getMonitorStatusByType(Map data) {
        //获取最新记录
        List<Map> re=monitorFeignService.getMonitorStatusByType(data);


        return re;
    }


    /**
     * 获取应用列表
     *
     * @return
     */
    public List<Map> getRefMonitorStatus(Map data) {
        List<Map> result=new ArrayList<>();
        //获取所有应用列表
        Map apps=serverFeignService.getAppsList(data);
        //获取所有的监控
        result=monitorFeignService.getMonitorByRef(apps);

        return result;
    }

    /**
     * 获取应用列表
     *
     * @return
     */
    public List<Map> getSerivceStatus(Map data) {
        List<Map> result=new ArrayList<>();

        //获取CPU百分比
        Map cpuMap=getCpuPercent((Map)data.get("cpuData"));

        //获取内存百分比
        Map memMap=getMemoryPercent((Map)data.get("memData"));

        //获取磁盘百分比
        Map diskMap=getDiskPercent((Map)data.get("diskData"));

        result.add(cpuMap);
        result.add(memMap);
        result.add(diskMap);

        return result;
    }

    /**
     * 获取应用列表
     *
     * @return
     */
    public List<Map> getSerivceIpStatus(Map timeData) {
        List<Map> result=new ArrayList<>();

        String serverType=String.valueOf(timeData.get("serverType"));
        Map pingTime= new HashMap();
        if("linux".equals(serverType)){
            pingTime=Ping.linuxPing(timeData.get("ip").toString(),(Integer) timeData.get("timeSize"));
        }else{
            pingTime=Ping.windowsPing(timeData.get("ip").toString(),(Integer) timeData.get("timeSize"), (Integer) timeData.get("timeOut"));

        }

        result.add(pingTime);

        return result;
    }

    /**
     * 获取CPU使用百分比
     * @param cpuData
     * @return
     */
    public  Map getCpuPercent(Map cpuData){
        HttpURLConnection uConnection = null;
        String o = "";
        try {
            //创建url链接
            uConnection = createConnection(cpuData.get("url").toString());
            //获取接口输出内容
            o = getResponseResult(uConnection);
        } catch (Exception ex) {
            o = API_CONNENT_ERROR;
        }finally {
            uConnection.disconnect();
        }

        String percent="";
        try {
            if (!StringUtils.isEmpty(o)) {
                String error = "";
                if (API_CONNENT_ERROR.equals(o)) {
                    //如果api连接异常直接告警
                    percent = "0";
                } else {
                    ScriptEngineManager m = new ScriptEngineManager();
                    //获取JavaScript执行引擎
                    ScriptEngine engine = m.getEngineByName("JavaScript");
                    engine.put("o", o);
                    engine.put("t", new Object());
                    engine.put("m", "");
                    //获取报警等级计算脚本
                    String js = "function getResult(){\nvar obj=JSON.parse(o);\n  if(obj.results==null||obj.results.length==0){\n    return 0;\n  }\n  if(obj.results[0].series==null||obj.results[0].series.length==0){\n    return 0;\n  }\n  if(obj.results[0].series[0].values==null||obj.results[0].series[0].values.length==0){\n    return 0;\n  };\n  var value=obj.results[0].series[0].values[0];\n  if(value==null||value.length<2){\n    return 0;\n  }\n  return value[1]; \n}\nt=getResult();";

                    engine.eval(js);

                    Invocable invocable = (Invocable) engine;
                    //获取百分比
                    percent =  invocable.invokeFunction("getResult").toString();
                }

            }
        } catch (Exception ex) {
            log.error("获取接口数据内容异常", ex);
        }

        //根据前台回传的标准判断颜色
        String color="#6cd888";
        if(Float.parseFloat(percent)>=Float.parseFloat(cpuData.get("errorLimit").toString())){
            color="#ff688f";
        }else if(Float.parseFloat(percent)>=Float.parseFloat(cpuData.get("warningLimit").toString())){
            color="#fff280";
        }

        //截取整数，防止页面显示变形
        if(percent.indexOf(".")>-1){
         percent=percent.substring(0,percent.indexOf("."));
        }
        Map m=new HashMap();
        m.put("title","CPU");
        m.put("percent",percent);
        m.put("color",color);
        return m;
    }

    /**
     * 获取内存使用百分比
     * @param memData
     * @return
     */
    public  Map getMemoryPercent(Map memData){
        HttpURLConnection uConnection = null;
        String o = "";
        try {
            //创建url链接
            uConnection = createConnection(memData.get("url").toString());
            //获取接口输出内容
            o = getResponseResult(uConnection);
        } catch (Exception ex) {
            o = API_CONNENT_ERROR;
        }finally {
            uConnection.disconnect();
        }

        String percent="";
        try {
            if (!StringUtils.isEmpty(o)) {
                String error = "";
                if (API_CONNENT_ERROR.equals(o)) {
                    //如果api连接异常直接告警
                    percent = "0";
                } else {
                    ScriptEngineManager m = new ScriptEngineManager();
                    //获取JavaScript执行引擎
                    ScriptEngine engine = m.getEngineByName("JavaScript");
                    engine.put("o", o);
                    engine.put("t", new Object());
                    engine.put("m", "");
                    //获取报警等级计算脚本
                    String js = "function getResult(){\nvar obj=JSON.parse(o);\n  if(obj.results==null||obj.results.length==0){\n    return 0;\n  }\n  if(obj.results[0].series==null||obj.results[0].series.length==0){\n    return 0;\n  }\n  if(obj.results[0].series[0].values==null||obj.results[0].series[0].values.length==0){\n    return 0;\n  };\n  var value=obj.results[0].series[0].values[0];\n  if(value==null||value.length<2){\n    return 0;\n  }\n  return value[1]; \n}\nt=getResult();";

                    engine.eval(js);

                    Invocable invocable = (Invocable) engine;
                    //获取百分比
                    percent =  invocable.invokeFunction("getResult").toString();
                }

            }
        } catch (Exception ex) {
            log.error("获取接口数据内容异常", ex);
        }

        //根据前台回传的标准判断颜色
        String color="#6cd888";
        if(Float.parseFloat(percent)>=Float.parseFloat(memData.get("errorLimit").toString())){
            color="#ff688f";
        }else if(Float.parseFloat(percent)>=Float.parseFloat(memData.get("warningLimit").toString())){
            color="#fff280";
        }

        //截取整数，防止页面显示变形
        if(percent.indexOf(".")>-1){
            percent=percent.substring(0,percent.indexOf("."));
        }
        Map m=new HashMap();
        m.put("title","内存");
        m.put("percent",percent);
        m.put("color",color);
        return m;
    }

    /**
     * 获取磁盘使用百分比
     * @param diskData
     * @return
     */
    public  Map getDiskPercent(Map diskData){
        HttpURLConnection uConnection = null;
        String o = "";
        try {
            //创建url链接
            uConnection = createConnection(diskData.get("url").toString());
            //获取接口输出内容
            o = getResponseResult(uConnection);
        } catch (Exception ex) {
            o = API_CONNENT_ERROR;
        }finally {
            uConnection.disconnect();
        }

        String percent="";
        try {
            if (!StringUtils.isEmpty(o)) {
                String error = "";
                if (API_CONNENT_ERROR.equals(o)) {
                    //如果api连接异常直接告警
                    percent = "0";
                } else {
                    ScriptEngineManager m = new ScriptEngineManager();
                    //获取JavaScript执行引擎
                    ScriptEngine engine = m.getEngineByName("JavaScript");
                    engine.put("o", o);
                    engine.put("t", new Object());
                    engine.put("m", "");
                    //获取报警等级计算脚本
                    String js = "function getResult(){\nvar obj=JSON.parse(o);\n  if(obj.results==null||obj.results.length==0){\n    return 0;\n  }\n  if(obj.results[0].series==null||obj.results[0].series.length==0){\n    return 0;\n  }\n  if(obj.results[0].series[0].values==null||obj.results[0].series[0].values.length==0){\n    return 0;\n  };\n  var value=obj.results[0].series[0].values[0];\n  if(value==null||value.length<2){\n    return 0;\n  }\n  return value[1]; \n}\nt=getResult();";

                    engine.eval(js);

                    Invocable invocable = (Invocable) engine;
                    //获取百分比
                    percent =  invocable.invokeFunction("getResult").toString();
                }

            }
        } catch (Exception ex) {
            log.error("获取接口数据内容异常", ex);
        }

        //根据前台回传的标准判断颜色
        String color="#6cd888";
        if(Float.parseFloat(percent)>=Float.parseFloat(diskData.get("errorLimit").toString())){
            color="#ff688f";
        }else if(Float.parseFloat(percent)>=Float.parseFloat(diskData.get("warningLimit").toString())){
            color="#fff280";
        }

        //截取整数，防止页面显示变形
        if(percent.indexOf(".")>-1){
            percent=percent.substring(0,percent.indexOf("."));
        }
        Map m=new HashMap();
        m.put("title","磁盘");
        m.put("percent",percent);
        m.put("color",color);
        return m;
    }

    /**
     * 创建url链接
     *
     * @author shenhj
     * @date 2018/4/27 10:05
     */
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

    /**
     * 获取接口输出内容
     *
     * @author shenhj
     * @date 2018/4/27 10:06
     */
    public String getResponseResult(HttpURLConnection httpConnection)
            throws IOException {
        String result = "";
        String str="";
        try {
            int responseCode = httpConnection.getResponseCode();
            switch (responseCode) {
                // 状态码200标识执行正常，状态码201表示，该内容已经被创见
                case 200:
                case 201:
                    BufferedReader responseBuffer1 = new BufferedReader(
                            new InputStreamReader(httpConnection.getInputStream(),
                                    "UTF-8"));
                    while ((str=responseBuffer1.readLine())!=null){


                        result+=str;
                    }
                    //result = responseBuffer1.readLine();
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

    /**
     * 获取服务器监控状态列表
     *
     * @return
     */
    public List<String> getSerivceMonitorStatus(Map data) {
        List<String> result=new ArrayList<>();

        //获取服务器状态百分比
        result=monitorFeignService.getSerivceMonitorStatus(data);


        return result;
    }
}
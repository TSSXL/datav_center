package com.smart.cityos.datav.service.feign.config;


import com.smart.cityos.datav.config.ApplicationProperties;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>title:配置中心</p>
 * <p>description:配置中心</p>
 *
 * @author:
 * @date Created in 2017-4-5
 */
@Service
@AllArgsConstructor
@Slf4j
public class MonitorFeignService {

    @Autowired
    private ApplicationProperties applicationProperties;


    private FeignServer createMonitorFeignServer() {
        return Feign.builder().encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignServer.class, applicationProperties.getMonitorControllerUrl());
    }

    private FeignServer createMonitorWebFeignServer() {
        return Feign.builder().encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignServer.class, applicationProperties.getMonitorWebControllerUrl());
    }


    /**
     * 通过feign获取监控状态总数
     */
    public List<Map> getTaskStatus(Map executeQueryParam) {
        FeignServer feignServer = createMonitorFeignServer();
        List<Map> list = feignServer.getTaskStatus(executeQueryParam);
        return list;
    }

    /**
     * 通过feign获取监控采集订阅数据量节点信息
     */
    public List<Map> getTaskDataNodesInfo(Map executeQueryParam) {
        FeignServer feignServer = createMonitorFeignServer();
        List<Map> result=new ArrayList<>();

        //获取作为数据量统计的采集。订阅节点
        List<List<String>> nodes = feignServer.getTaskDataNodesInfo();

        List<Map> collList =getNodeDataCount(nodes.get(0),"1");
        List<Map> subList =getNodeDataCount(nodes.get(1),"2");


        collList.addAll(subList);
        result=collList;

        return result;
    }

    /**
     * 获取节点数据量
     * @return
     */
    public List<Map> getNodeDataCount(List<String> nodes,String type){
        FeignServer feignWebServer = createMonitorWebFeignServer();
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH");

        String[] hours=new String[12];
        for(int i=0;i<12;i++){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR_OF_DAY, 0-i);
            hours[11-i]=dFormat.format(cal.getTime());
        }
        //初始化查询参数
        Map queryMap=new HashMap();
        queryMap.put("endTime",hours[11]);
        queryMap.put("startTime",hours[0]);
        queryMap.put("nodeIds",nodes);
        //获取数据量(按小时)
        List<Map> collData = feignWebServer.getNodeDataByHour(queryMap);
        //循环时间，对比获取的数据，如果有则替换数据量，没有则为0
        // (数据量为0时不会记录数据库，所以需要自己生成)
        List<Map> list=new ArrayList<>();
        for(int i=0;i<hours.length;i++){
            Map map=new HashMap();
            map.put("x",hours[i]);
            map.put("y",0);
            map.put("s",type);
            //循环数据量信息
            collData.forEach(data->{
                if(map.get("x").equals(data.get("date"))){
                    map.put("y",data.get("outCount"));
                }
            });
            map.put("x",hours[i].split(" ")[1]+"时");
            list.add(map);
        }
        return list;
    }

    /**
     * 通过feign获取监控采集订阅数据量节点信息
     */
    public List<Map> getTaskErrorCount(Map executeQueryParam) {
        FeignServer feignServer = createMonitorFeignServer();
        List<Map> result=new ArrayList<>();
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");

        String[] days=new String[7];
        for(int i=0;i<7;i++){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 0-i);
            days[6-i]=dFormat.format(cal.getTime());
        }
        executeQueryParam.put("endTime",days[6]+"T23:59:59.999Z");
        executeQueryParam.put("startTime",days[0]+"T00:00:00.000Z");

        List<Map> re=feignServer.getTaskErrorCount(executeQueryParam);
        List<Map> war=new ArrayList<>();
        List<Map> err=new ArrayList<>();
        //根据等级区分成两个集合
        re.forEach(r->{
            if("1".equals(r.get("alertLevel"))){
                war.add(r);
            }else{
                err.add(r);
            }
        });

        result=getTaskErrorDataFormat(war,days,"2");
        result.addAll(getTaskErrorDataFormat(err,days,"1"));


        return result;
    }

    public List<Map> getTaskErrorDataFormat(List<Map> data,String[] days,String type){
        List<Map> result=new ArrayList<>();

        for(int i=0;i<days.length;i++){
            Map map=new HashMap();
            map.put("s",type);
            map.put("x",days[i]);
            map.put("y",0);
            data.forEach(d->{
                if(map.get("x").equals(d.get("date"))){
                    map.put("y",d.get("count"));
                }
            });
            String[] day=days[i].split("-");
            map.put("x",day[1]+"/"+day[2]);
            result.add(map);
        }




        return result;
    }

    /**
     * 获取采集、订阅中心数据量显示详情
     * @param qmap
     * @return
     */
    public List<Map> getTaskDataInfoCount(Map qmap){
        FeignServer feignServer = createMonitorFeignServer();
        //节点
        List<String> node=new ArrayList<>();

        List<Map> reslut=new ArrayList<>();
        //获取作为数据量统计的采集。订阅节点
        List<List<String>> nodes = feignServer.getTaskDataNodesInfo();
        if("coll".equals(qmap.get("type"))){
            node=nodes.get(0);
        }else{
            node=nodes.get(1);
        }
        reslut=getTaskDataInfoCountFormat(node);

        return reslut;
    }

    public List<Map> getTaskDataInfoCountFormat(List<String> nodes){
        List<Map> reslut=new ArrayList<>();
        Map todayIn=new HashMap();
        Map in=new HashMap();
        Map out=new HashMap();
        FeignServer feignWebServer = createMonitorWebFeignServer();
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        //初始化查询参数
        Map queryMap=new HashMap();
        queryMap.put("endTime",dFormat.format(new Date()));
        queryMap.put("startTime",dFormat.format(new Date()));
        queryMap.put("nodeIds",nodes);
        //获取今日数据量
        List<Map> dayData = feignWebServer.getNodeDataByDay(queryMap);

        //获取总数据量
        List<Map> outCount=feignWebServer.getNodeAllData(queryMap);
        if(dayData.size()>0){
            todayIn.put("value",dayData.get(0).get("outCount"));
        }else{
            todayIn.put("value",0);
        }
        if(outCount.size()>0){
            out.put("value",outCount.get(0).get("outCount"));
            in.put("value",outCount.get(0).get("outCount"));
        }else{
            out.put("value",0);
            in.put("value",0);
        }
        reslut.add(out);
        reslut.add(in);
        reslut.add(todayIn);



        return reslut;
    }

    /**
     * 获取数据库状态
     * @param data
     * @return
     */
    public List<Map> getDbStatus(Map data){
        List<Map> result=new ArrayList<>();
        FeignServer feignServer = createMonitorFeignServer();
        Map reMap=new HashMap();

        Map re=feignServer.getMonitorInfoBySrcId(data);
        if(re==null){
            reMap.put("value",data.get("errorImg"));
        }else if(Integer.parseInt(re.get("alertLevel").toString())==0){
            reMap.put("value",data.get("successImg"));
        }else if(Integer.parseInt(re.get("alertLevel").toString())==1){
            reMap.put("value",data.get("warningImg"));
        }else{
            reMap.put("value",data.get("errorImg"));
        }
        result.add(reMap);

        return result;

    }

    /**
     * 获取数据库状态
     * @param data
     * @return
     */
    public List<Map> getMonitorStatusByType(Map data){
        List<Map> result=new ArrayList<>();
        FeignServer feignServer = createMonitorFeignServer();


        List<Map> re=feignServer.getMonitorStatusByType(data);

        //循环生成状态
        for(int i=0;i<3;i++){
            Map map=new HashMap();
            map.put("value",0);
            int index=i;
            re.forEach(r->{
                if(Integer.parseInt(r.get("_id").toString())==index){
                    map.put("value",r.get("count"));
                }
            });
            result.add(map);
        }

        return result;

    }

    /**
     * 获取应用状态
     * @param apps
     * @return
     */
    public List<Map> getMonitorByRef(Map apps){
        List<Map> result=new ArrayList<>();
        FeignServer feignServer = createMonitorFeignServer();

        //获取监控状态
        List<Map> status=feignServer.getMonitorByRef();
        List appList=(List)((Map)apps.get("data")).get("results");

        int[] counts=getRefMonitorStatusFormat(appList,status);

        for(int i=0;i<counts.length;i++){
            Map map=new HashMap();
            map.put("value",counts[i]);
            result.add(map);
        }
        return result;

    }

    public int[]  getRefMonitorStatusFormat(List apps,List<Map> monitors){

        int successCount=0;
        int warningCount=0;
        int errorCount=0;
        int nullCount=0;
        for(int k=0;k<apps.size();k++){
            //循环应用列表
            Map app=(Map)apps.get(k);

             int status=-1;
            for(int i=0;i<monitors.size();i++) {
                //循环监控列表，判断应用最大的监控状态
                    Map m=monitors.get(i);
                if(app.get("_id").equals(m.get("refApp"))){
                    if(Integer.parseInt(m.get("alertLevel").toString())>status){
                        status=Integer.parseInt(m.get("alertLevel").toString());
                    }
                }
            }
            //根据状态计算数量
            if(status==2){
                errorCount++;
            }else if(status==1){
                warningCount++;
            }else if(status==0){
                successCount++;
            }else{
                nullCount++;
            }

            }
        int[] result={successCount,warningCount,errorCount,nullCount};

        return result;
    }
}

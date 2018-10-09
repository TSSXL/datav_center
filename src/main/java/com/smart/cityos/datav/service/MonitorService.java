package com.smart.cityos.datav.service;

import com.smart.cityos.datav.service.feign.config.MonitorFeignService;
import com.smart.cityos.datav.service.feign.config.ServerFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



}
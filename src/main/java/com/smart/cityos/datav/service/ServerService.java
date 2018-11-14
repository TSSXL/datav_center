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
 * server平台类
 *
 * @author: mingcheong
 * @date Created in 2018-08-27
 */
@Slf4j
@Service
public class ServerService {

    @Autowired
    private ServerFeignService serverFeignService;

    /**
     * 获取任务总数
     *
     * @return
     */
    public List<Map> getTaskCount(Map data) {

        List<Map> result=new ArrayList<Map>();

        //获取最新记录
        Map re=serverFeignService.getTaskCount(data);
        Map da=(Map)re.get("data");
        List<Map> res=(List)da.get("results");
        if(res.size()>0){
            res.forEach(r->{
                Map map=new HashMap();
                map.put("value",r.get("scount"));
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




}
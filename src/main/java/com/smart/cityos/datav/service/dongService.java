package com.smart.cityos.datav.service;

import com.smart.cityos.datav.service.feign.config.ConfigFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 洞桥镇环保地图类
 *
 * @author: ts
 * @date Created in 2019-1-6
 */
@Slf4j
@Service
public class dongService {

    @Autowired
    private ConfigFeignService configFeignService;

    /**
     * 获取企业总数列表
     *
     */

    public List<Map> getCompanyNumList(Map data) {

        List<Map> result=new ArrayList<Map>();
        Map nowQuery=new HashMap();
        nowQuery.put("dbInfo",data.get("dbInfo"));
        nowQuery.put("sql",data.get("sql"));
        //获取最新记录
        List<Map> re=configFeignService.executeQuery(nowQuery);
        re.forEach(v -> {
            v.put("id",v.get("GDSBID"));
            v.put("qymc",v.get("QYMC")+"_"+v.get("PKMC"));
            result.add(v);
        });
        return result;
    }

    /**
     * 获取行业大类占比列表
     *
     */

    public List<Map> getCategoryList(Map data) {

        List<Map> result=new ArrayList<Map>();
        Map nowQuery=new HashMap();
        nowQuery.put("dbInfo",data.get("dbInfo"));
        nowQuery.put("sql",data.get("sql"));
        //获取最新记录
        List<Map> re=configFeignService.executeQuery(nowQuery);
        re.forEach(v -> {
            v.put("id",v.get("GDSBID"));
            v.put("qymc",v.get("QYMC")+"_"+v.get("PKMC"));
            result.add(v);
        });
        return result;
    }

    /**
     * 获取企业规模占比列表
     *
     */

    public List<Map> getCompanySizeList(Map data) {

        List<Map> result=new ArrayList<Map>();
        Map nowQuery=new HashMap();
        nowQuery.put("dbInfo",data.get("dbInfo"));
        nowQuery.put("sql",data.get("sql"));
        //获取最新记录
        List<Map> re=configFeignService.executeQuery(nowQuery);
        re.forEach(v -> {
            v.put("id",v.get("GDSBID"));
            v.put("qymc",v.get("QYMC")+"_"+v.get("PKMC"));
            result.add(v);
        });
        return result;
    }

    /**
     * 获取运行状态占比列表
     *
     */

    public List<Map> getStatusList(Map data) {

        List<Map> result=new ArrayList<Map>();
        Map nowQuery=new HashMap();
        nowQuery.put("dbInfo",data.get("dbInfo"));
        nowQuery.put("sql",data.get("sql"));
        //获取最新记录
        List<Map> re=configFeignService.executeQuery(nowQuery);
        re.forEach(v -> {
            v.put("id",v.get("GDSBID"));
            v.put("qymc",v.get("QYMC")+"_"+v.get("PKMC"));
            result.add(v);
        });
        return result;
    }

    /**
     * 获取地图列表
     *
     */

    public List<Map> getMapList(Map data) {

        List<Map> result=new ArrayList<Map>();
        Map nowQuery=new HashMap();
        nowQuery.put("dbInfo",data.get("dbInfo"));
        nowQuery.put("sql",data.get("sql"));
        //获取最新记录
        List<Map> re=configFeignService.executeQuery(nowQuery);
        re.forEach(v -> {
            v.put("id",v.get("GDSBID"));
            v.put("qymc",v.get("QYMC")+"_"+v.get("PKMC"));
            result.add(v);
        });
        return result;
    }
}

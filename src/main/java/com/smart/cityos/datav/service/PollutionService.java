package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.model.AQI7days;
import com.smart.cityos.datav.domain.model.AQITrend;
import com.smart.cityos.datav.service.feign.config.ConfigFeignService;
import com.smart.cityos.datav.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 空气质量业务类
 *
 * @author: mingcheong
 * @date Created in 2018-08-27
 */
@Slf4j
@Service
public class PollutionService {

    @Autowired
    private ConfigFeignService configFeignService;

    /**
     * 获取废水企业排口列表
     *
     * @return 12小时AQI趋势模型集合
     */
    public List<Map> getFsQyInfoList(Map data) {

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
     * 获取废气企业排口列表
     *
     * @return 12小时AQI趋势模型集合
     */
    public List<Map> getGasQyInfoList(Map data) {

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
     * 获取企业地理坐标列表
     *
     * @return 12小时AQI趋势模型集合
     */
    public List<Map> getQyMapInfoList(Map data) {


        Map nowQuery=new HashMap();
        nowQuery.put("dbInfo",data.get("dbInfo"));
        nowQuery.put("sql",data.get("sql"));
        //获取最新记录
        List<Map> result=configFeignService.executeQuery(nowQuery);

        return result;
    }


    /**
     * 获取废气企业详细信息
     *
     * @return 12小时AQI趋势模型集合
     */
    public List<Map> getGasQyInfo(Map data) {

        List<Map> result=new ArrayList<Map>();
        String sql=""+data.get("sql");
        sql=sql.replace("#id#",String.valueOf(data.get("id")));
        Map nowQuery=new HashMap();
        nowQuery.put("dbInfo",data.get("dbInfo"));
        nowQuery.put("sql",sql);
        System.out.println(sql);
        //获取最新记录
        List<Map> re=configFeignService.executeQuery(nowQuery);
        Map map=new HashMap();
        map.put("qy",re.get(0).get("QYMC"));
        List<Map> pk=new ArrayList<Map>();
        re.forEach(v -> {
            v.put("id",v.get("GDSBID"));
            v.put("pkmc",v.get("QYMC")+"_"+v.get("PKMC"));
            pk.add(v);
        });
        map.put("pk",pk);
        result.add(map);

        return result;
    }

    /**
     * 获取废水企业详细信息
     *
     * @return 12小时AQI趋势模型集合
     */
    public List<Map> getFsQyInfo(Map data) {

        List<Map> result=new ArrayList<Map>();
        String sql=""+data.get("sql");
        sql=sql.replace("#id#",String.valueOf(data.get("id")));
        Map nowQuery=new HashMap();
        nowQuery.put("dbInfo",data.get("dbInfo"));
        nowQuery.put("sql",sql);
        System.out.println(sql);
        //获取最新记录
        List<Map> re=configFeignService.executeQuery(nowQuery);
        Map map=new HashMap();
        map.put("qy",re.get(0).get("QYMC"));
        List<Map> pk=new ArrayList<Map>();
        re.forEach(v -> {
            v.put("id",v.get("GDSBID"));
            v.put("pkmc",v.get("QYMC")+"_"+v.get("PKMC"));
            pk.add(v);
        });
        map.put("pk",pk);
        result.add(map);

        return result;
    }
}
package com.smart.cityos.datav.service;

import com.smart.cityos.datav.service.feign.config.ConfigFeignService;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 空气质量业务类
 *
 * @author: mingcheong
 * @date Created in 2018-08-27
 */
@Slf4j
@Service
public class TestApiService {

    @Autowired
    private ConfigFeignService configFeignService;

    /**
     * 返回样本数据
     *
     * @return 12小时AQI趋势模型集合
     */
    public List<Map> getTestApiInfo(Map data) {

        List<Map> result=new ArrayList<Map>();
        if("groupBar".equals(data.get("type"))){
            result=returnGroupBarData(data);
        }else if("barWithLine".equals(data.get("type"))){
            result=returnBarWithLineData(data);
        }else if("baseLine".equals(data.get("type"))){
            result=returnBaseLineData(data);
        }else if("basePie".equals(data.get("type"))){
            result=returnBasePieData(data);
        }else if("bubble".equals(data.get("type"))){
            result=returnBubbleData(data);
        }else if("baseRardar".equals(data.get("type"))){
            result=returnBaseRardarData(data);
        }else if("baseScatter".equals(data.get("type"))){
            result=returnBaseScatterData(data);
        }else if("pieMutliPercent".equals(data.get("type"))){
            result=returnPieMultiPercentData(data);
        }

        return result;
    }

    /**
     * 返回样本数据Map类型
     *
     * @return 12小时AQI趋势模型集合
     */
    public Map getTestApiMapInfo(Map data) {

        Map result=new HashMap();
        if("piePercent".equals(data.get("type"))){
            result=returnPiePercentData(data);
        }else if("macroEconomy".equals(data.get("type"))){
            result=returnMacroEconomyData(data);
        }

        return result;
    }

    /**
     * 返回分组柱图样本数据
     *
     * @return 分组柱图样本数据
     */
    public List<Map> returnGroupBarData(Map data) {

        List<Map> result=new ArrayList<Map>();
        Map m1=new HashMap();
        m1.put("s","1");
        m1.put("y","200");
        m1.put("x","浙江");
        Map m2=new HashMap();
        m2.put("s","2");
        m2.put("y","50");
        m2.put("x","浙江");
        Map m3=new HashMap();
        m3.put("s","1");
        m3.put("y","150");
        m3.put("x","辽宁");
        Map m4=new HashMap();
        m4.put("s","2");
        m4.put("y","100");
        m4.put("x","辽宁");
        result.add(m1);
        result.add(m2);
        result.add(m3);
        result.add(m4);

        return result;
    }

    /**
     * 返回折线柱图样本数据
     *
     * @return 折线柱图样本数据
     */
    public List<Map> returnBarWithLineData(Map data) {

        List<Map> result=new ArrayList<Map>();
        Map m1=new HashMap();
        m1.put("s","1");
        m1.put("y","169");
        m1.put("x","2013");
        Map m2=new HashMap();
        m2.put("s","1");
        m2.put("y","50");
        m2.put("x","2014");
        Map m3=new HashMap();
        m3.put("s","1");
        m3.put("y","150");
        m3.put("x","2015");
        Map m4=new HashMap();
        m4.put("s","1");
        m4.put("y","100");
        m4.put("x","2016");
        result.add(m1);
        result.add(m2);
        result.add(m3);
        result.add(m4);

        return result;
    }

    /**
     * 返回基本折线图样本数据
     *
     * @return 基本折线图样本数据
     */
    public List<Map> returnBaseLineData(Map data) {

        List<Map> result=new ArrayList<Map>();
        Map m1=new HashMap();
        m1.put("s","1");
        m1.put("y","169");
        m1.put("x","2010/01/01 00:00:00");
        Map m2=new HashMap();
        m2.put("s","2");
        m2.put("y","50");
        m2.put("x","2010/01/01 00:00:00");
        Map m3=new HashMap();
        m3.put("s","3");
        m3.put("y","150");
        m3.put("x","2010/01/01 00:00:00");
        Map m4=new HashMap();
        m4.put("s","1");
        m4.put("y","100");
        m4.put("x","2010/02/01 00:00:00");
        Map m5=new HashMap();
        m5.put("s","2");
        m5.put("y","121");
        m5.put("x","2010/02/01 00:00:00");
        Map m6=new HashMap();
        m6.put("s","3");
        m6.put("y","53");
        m6.put("x","2010/02/01 00:00:00");
        result.add(m1);
        result.add(m2);
        result.add(m3);
        result.add(m4);
        result.add(m5);
        result.add(m6);

        return result;
    }

    /**
     * 返回基本饼图样本数据
     *
     * @return 基本基本饼图样本数据
     */
    public List<Map> returnBasePieData(Map data) {

        List<Map> result=new ArrayList<Map>();
        Map m1=new HashMap();
        m1.put("y","169");
        m1.put("x","设备");
        Map m2=new HashMap();
        m2.put("y","50");
        m2.put("x","食品");
        Map m3=new HashMap();
        m3.put("y","150");
        m3.put("x","建材");

        result.add(m1);
        result.add(m2);
        result.add(m3);

        return result;
    }

    /**
     * 返回气泡图样本数据
     *
     * @return 基本气泡图样本数据
     */
    public List<Map> returnBubbleData(Map data) {

        List<Map> result=new ArrayList<Map>();
        Map m1=new HashMap();
        m1.put("s","1");
        m1.put("r","20");
        m1.put("y","169");
        m1.put("x","2010/01/01");
        Map m2=new HashMap();
        m2.put("s","1");
        m2.put("r","40");
        m2.put("y","50");
        m2.put("x","2010/02/01");
        Map m3=new HashMap();
        m3.put("s","1");
        m3.put("r","50");
        m3.put("y","150");
        m3.put("x","2010/03/01");

        result.add(m1);
        result.add(m2);
        result.add(m3);

        return result;
    }

    /**
     * 返回基本雷达图样本数据
     *
     * @return 基本雷达图样本数据
     */
    public List<Map> returnBaseRardarData(Map data) {

        List<Map> result=new ArrayList<Map>();
        Map m1=new HashMap();
        List<Object> da=new ArrayList<Object>();
        List<Object> num=new ArrayList<Object>();
        num.add(55);
        num.add(9);
        num.add(56);
        num.add(0.46);
        num.add(18);
        num.add(6);
        num.add(1);
        da.add(num);
        m1.put("data",da);
        m1.put("s","北京");
        Map m2=new HashMap();
        m2.put("data",da);
        m2.put("s","上海");
        Map m3=new HashMap();
        m3.put("data",da);
        m3.put("s","广州");


        result.add(m1);
        result.add(m2);
        result.add(m3);

        return result;
    }

    /**
     * 返回基本散点图样本数据
     *
     * @return 基本散点图样本数据
     */
    public List<Map> returnBaseScatterData(Map data) {
        Random ra =new Random();
        List<Map> result= new ArrayList<>();
        Map m1=new HashMap();
        m1.put("s","1");
        m1.put("y",ra.nextInt(100));
        m1.put("x",ra.nextInt(100));

        Map m2=new HashMap();
        m2.put("s","1");
        m2.put("y",ra.nextInt(100));
        m2.put("x",ra.nextInt(100));

        Map m3=new HashMap();
        m3.put("s","2");
        m3.put("y",ra.nextInt(100));
        m3.put("x",ra.nextInt(100));

        Map m4=new HashMap();
        m4.put("s","2");
        m4.put("y",ra.nextInt(100));
        m4.put("x",ra.nextInt(100));

        Map m5=new HashMap();
        m5.put("s","3");
        m5.put("y",ra.nextInt(100));
        m5.put("x",ra.nextInt(100));

        Map m6=new HashMap();
        m6.put("s","3");
        m6.put("y",ra.nextInt(100));
        m6.put("x",ra.nextInt(100));



        result.add(m1);
        result.add(m2);
        result.add(m3);
        result.add(m4);
        result.add(m5);
        result.add(m6);

        return result;
    }

    /**
     * 返回单值百分比饼图样本数据
     *
     * @return 单值百分比饼图样本数据
     */
    public Map returnPiePercentData(Map data) {

        Map result=new HashMap(1);
        result.put("value",57);

        return result;
    }

    /**
     * 返回多值百分比饼图样本数据
     *
     * @return 多值百分比饼图样本数据
     */
    public List<Map> returnPieMultiPercentData(Map data) {

        List<Map> result= new ArrayList<>();
        Map m1=new HashMap(2);
        m1.put("value",57);
        m1.put("total",235);
        Map m2=new HashMap(2);
        m2.put("value",23);
        m2.put("total",180);
        Map m3=new HashMap(2);
        m3.put("value",123);
        m3.put("total",170);

        result.add(m1);
        result.add(m2);
        result.add(m3);
        return result;
    }

    /**
     * 返回宏观经济样本数据
     *
     * @return 宏观经济样本数据
     */
    public Map returnMacroEconomyData(Map data) {

        Map result=new HashMap();
        result.put("val",51);
        result.put("text","占比");


        return result;
    }


}

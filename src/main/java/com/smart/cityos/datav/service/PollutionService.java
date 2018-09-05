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
        Map qyF=new HashMap();
        qyF.put("qymc","宁波中华纸业有限公司  #1");
        qyF.put("ph","7.957");
        qyF.put("hxxyl","65.36");
        qyF.put("fsll","7.957");
        qyF.put("jcsj","2018-09-01 14时");

        Map qyS=new HashMap();
        qyS.put("qymc","宁波市江东北区污水处理厂#1");
        qyS.put("ph","6.444");
        qyS.put("hxxyl","21.6");
        qyS.put("fsll","6.444");
        qyS.put("jcsj","2018-09-01 14时");

        Map qyT=new HashMap();
        qyT.put("qymc","宁波经济技术开发区伟伟染业有限公司#1");
        qyT.put("ph","6.444");
        qyT.put("hxxyl","21.6");
        qyT.put("fsll","6.444");
        qyT.put("jcsj","2018-09-01 14时");

        result.add(qyF);
        result.add(qyS);
        result.add(qyT);
        return result;
    }

    /**
     * 获取废气企业排口列表
     *
     * @return 12小时AQI趋势模型集合
     */
    public List<Map> getGasQyInfoList(Map data) {

        List<Map> result=new ArrayList<Map>();

        Map qyF=new HashMap();
        qyF.put("qymc","国电浙江北仑第一发电有限公司#2");
        qyF.put("so2zs","16.2669");
        qyF.put("yczs","3.44");
        qyF.put("noxzs","37.91");
        qyF.put("yqll","1821483.14");
        qyF.put("jcsj","2018-09-01 14时");

        Map qyS=new HashMap();
        qyS.put("qymc","国电浙江北仑第一发电有限公司#1");
        qyS.put("so2zs","16.2669");
        qyS.put("yczs","3.44");
        qyS.put("noxzs","37.91");
        qyS.put("yqll","1821483.14");
        qyS.put("jcsj","2018-09-01 14时");

        Map qyT=new HashMap();
        qyT.put("qymc","浙江浙能北仑发电有限公司#1");
        qyT.put("so2zs","16.2669");
        qyT.put("yczs","3.44");
        qyT.put("noxzs","37.91");
        qyT.put("yqll","1821483.14");
        qyT.put("jcsj","2018-09-01 14时");

        result.add(qyF);
        result.add(qyS);
        result.add(qyT);
        return result;
    }



}
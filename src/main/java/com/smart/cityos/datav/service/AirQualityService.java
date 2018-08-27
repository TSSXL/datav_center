package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.model.AQITrend;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 空气质量业务类
 *
 * @author: mingcheong
 * @date Created in 2018-08-27
 */
@Service
public class AirQualityService {

    /**
     * 获取12小时AQI趋势数据列表
     *
     * @return 12小时AQI趋势模型集合
     */
    public List<AQITrend> get12HourAQITrend() {

        List<AQITrend> list = new ArrayList<>();
        list.add(new AQITrend("01时", 24));
        list.add(new AQITrend("02时", 45));
        list.add(new AQITrend("03时", 67));
        list.add(new AQITrend("04时", 23));
        list.add(new AQITrend("05时", 45));
        list.add(new AQITrend("06时", 102));
        list.add(new AQITrend("07时", 34));
        list.add(new AQITrend("08时", 54));
        list.add(new AQITrend("09时", 56));
        list.add(new AQITrend("10时", 15));
        list.add(new AQITrend("11时", 73));
        list.add(new AQITrend("12时", 74));
        return list;
    }

}
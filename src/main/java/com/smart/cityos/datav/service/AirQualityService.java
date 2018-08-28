package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.model.AQI7days;
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
    public List<AQITrend> get12HourAQITrend(String id) {

        List<AQITrend> list = new ArrayList<AQITrend>();
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


    /**
     * 获取7天AQI柱状图数据列表
     *
     * @return AQI柱状图数据列表
     */
    public List<AQI7days> get7DaysAQITrend(String id) {
        List<AQI7days> list = new ArrayList<>();
        list.add(new AQI7days("08/18", 24, "1"));
        list.add(new AQI7days("08/18", 0, "2"));
        list.add(new AQI7days("08/18", 0, "3"));
        list.add(new AQI7days("08/19", 40, "1"));
        list.add(new AQI7days("08/19", 0, "2"));
        list.add(new AQI7days("08/19", 0, "3"));
        list.add(new AQI7days("08/20", 36, "1"));
        list.add(new AQI7days("08/20", 0, "2"));
        list.add(new AQI7days("08/20", 0, "3"));
        list.add(new AQI7days("08/21", 0, "1"));
        list.add(new AQI7days("08/21", 70, "2"));
        list.add(new AQI7days("08/21", 0, "3"));
        list.add(new AQI7days("08/22", 90, "1"));
        list.add(new AQI7days("08/22", 0, "2"));
        list.add(new AQI7days("08/22", 0, "3"));
        list.add(new AQI7days("08/23", 49, "1"));
        list.add(new AQI7days("08/23", 0, "2"));
        list.add(new AQI7days("08/23", 0, "3"));
        list.add(new AQI7days("08/24", 0, "1"));
        list.add(new AQI7days("08/24", 0, "2"));
        list.add(new AQI7days("08/24", 101, "3"));
        return list;
    }


    /**
     * 根据日历获取降雨量数据列表
     *
     * @return 降雨量数据列表
     */
    public List<Integer> getCalendarAQl(String id, String date) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        list.add(11);
        list.add(12);
        list.add(13);
        list.add(14);
        list.add(15);
        list.add(16);
        list.add(17);
        list.add(18);
        list.add(19);
        list.add(20);
        list.add(21);
        list.add(22);
        list.add(23);
        list.add(24);
        list.add(25);
        list.add(26);
        list.add(27);
        list.add(28);
        list.add(29);
        list.add(30);
        list.add(31);
        return list;
    }

}
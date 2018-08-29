package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.model.AQI7days;
import com.smart.cityos.datav.domain.model.AQITrend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 空气质量业务类
 *
 * @author: mingcheong
 * @date Created in 2018-08-27
 */
@Slf4j
@Service
public class AirQualityService {

    /**
     * 获取12小时AQI趋势数据列表
     *
     * @return 12小时AQI趋势模型集合
     */
    public List<AQITrend> get12HourAQITrend(String id) {

        List<AQITrend> list = new ArrayList<AQITrend>();
        if (id.equals("0")) {
            list.add(new AQITrend("01时", 45));
            list.add(new AQITrend("02时", 55));
            list.add(new AQITrend("03时", 66));
            list.add(new AQITrend("04时", 77));
            list.add(new AQITrend("05时", 88));
            list.add(new AQITrend("06时", 33));
            list.add(new AQITrend("07时", 44));
            list.add(new AQITrend("08时", 66));
            list.add(new AQITrend("09时", 88));
            list.add(new AQITrend("10时", 11));
            list.add(new AQITrend("11时", 98));
            list.add(new AQITrend("12时", 110));
        } else {
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
        }
        return list;
    }


    /**
     * 获取7天AQI柱状图数据列表
     *
     * @return AQI柱状图数据列表
     */
    public List<AQI7days> get7DaysAQITrend(String id) {
        List<AQI7days> list = new ArrayList<>();
        if (id.equals("0")) {
            list.add(new AQI7days("08/18", 2, "1"));
            list.add(new AQI7days("08/18", 20, "2"));
            list.add(new AQI7days("08/18", 30, "3"));
            list.add(new AQI7days("08/19", 90, "1"));
            list.add(new AQI7days("08/19", 1, "2"));
            list.add(new AQI7days("08/19", 20, "3"));
            list.add(new AQI7days("08/20", 22, "1"));
            list.add(new AQI7days("08/20", 10, "2"));
            list.add(new AQI7days("08/20", 30, "3"));
            list.add(new AQI7days("08/21", 20, "1"));
            list.add(new AQI7days("08/21", 20, "2"));
            list.add(new AQI7days("08/21", 0, "3"));
            list.add(new AQI7days("08/22", 60, "1"));
            list.add(new AQI7days("08/22", 20, "2"));
            list.add(new AQI7days("08/22", 10, "3"));
            list.add(new AQI7days("08/23", 0, "1"));
            list.add(new AQI7days("08/23", 29, "2"));
            list.add(new AQI7days("08/23", 32, "3"));
            list.add(new AQI7days("08/24", 22, "1"));
            list.add(new AQI7days("08/24", 65, "2"));
            list.add(new AQI7days("08/24", 10, "3"));
        } else {
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
        }
        return list;
    }


    /**
     * 根据日历获取降雨量数据列表
     *
     * @return 降雨量数据列表
     */
    public List<String[]> getCalendarAQl(String id, String date) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sDateFormat.parse(date);
            log.debug("执行日期格式化 : {}", date);
        } catch (ParseException px) {
            d = new Date();
            log.error("日期格式错误", px);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date begin = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = cal.getTime();

        List<String[]> result = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        while (begin.getTime() <= end.getTime()) {
            result.add(new String[]{dFormat.format(tempStart.getTime()), "0"});
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }

        Integer[] month7 = new Integer[]{
                30, 40, 40, 50, 50, 50, 50, 90, 90, 10,
                30, 100, 100, 100, 40, 40, 40, 40, 40, 40,
                20, 20, 20, 20, 20, 20, 20, 50, 50, 0,
                0
        };
        Integer[] month8 = new Integer[]{
                30, 100, 100, 100, 40, 40, 40, 40, 40, 40,
                20, 20, 20, 20, 20, 20, 20, 50, 50, 0,
                30, 40, 40, 50, 50, 50, 50, 90, 90, 10,
                0
        };

        for (int i = 0; i < result.size(); i++) {
            if (begin.getMonth() == 8) {
                result.get(i)[1] = String.valueOf(month8[i]);
            } else {
                result.get(i)[1] = String.valueOf(month7[i]);
            }
        }
        return result;
    }

}
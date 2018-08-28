package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.domain.Result;
import com.smart.cityos.datav.service.AirQualityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 环保空气质量接口
 *
 * @author: mingcheong
 * @date Created in 2018-08-24
 */
@RestController
@RequestMapping("/api/airquality")
@Slf4j
@AllArgsConstructor
@Api(value = "AirQualityController", description = "环保空气质量接口")
public class AirQualityController {

    /**
     * 环保空气质量业务操作对象
     */
    @Autowired
    private AirQualityService airQualityService;

    @ApiOperation("获取12小时AQI趋势数据列表")
    @GetMapping(value = "/aqitrend/{id}")
    public Result get12HourAQITrend(@PathVariable String id) {
        log.debug("根据ID获取12小时AQI趋势数据列表 : {}", id);
        return new Result(airQualityService.get12HourAQITrend(id));
    }


    @ApiOperation("获取7天AQI柱状图数据列表")
    @GetMapping(value = "/aqi7days/{id}")
    public Result get7DaysAQITrend(@PathVariable String id) {
        log.debug("根据ID获取7天AQI柱状图数据列表 : {}", id);
        return new Result(airQualityService.get7DaysAQITrend(id));
    }

    @ApiOperation("根据日历获取AQI数据列表")
    @PostMapping(value = "/aqicalendar/{id}")
    public Result getCalendarAQI(@PathVariable String id, @RequestBody String date) {
        log.debug("根据ID获取日历AQI数据列表 : {}", id);
        return new Result(airQualityService.getCalendarAQl(id, date));
    }
}

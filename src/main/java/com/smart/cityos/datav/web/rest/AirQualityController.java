package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.domain.Result;
import com.smart.cityos.datav.service.AirQualityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @PostMapping(value = "/aqitrend")
    public Result get12HourAQITrend(@RequestBody Map data) {
        log.debug("根据ID获取12小时AQI趋势数据列表 : {}");
        return new Result(airQualityService.get12HourAQITrend(data));
    }


    @ApiOperation("获取7天AQI柱状图数据列表")
    @GetMapping(value = "/aqi7days/{id}")
    public Result get7DaysAQITrend(@PathVariable String id) {
        log.debug("根据ID获取7天AQI柱状图数据列表 : {}", id);
        return new Result(airQualityService.get7DaysAQITrend(id));
    }

    @ApiOperation("根据日历获取AQI数据列表")
    @PostMapping(value = "/aqicalendar")
    public Result getCalendarAQI(@RequestBody Map data) {
        return new Result(airQualityService.getCalendarAQl(data));
    }

    @ApiOperation("获取宁波天气信息")
    @GetMapping(value = "/getWeathers")
    public Result getWeathers() {
        log.debug("获取宁波天气信息");
        return new Result(airQualityService.getWeathers());
    }

    @ApiOperation("获取站点状态列表")
    @PostMapping(value = "/getStationInfoList")
    public Result getStationInfoList(@RequestBody Map data) {
        log.debug("根据ID获取12小时AQI趋势数据列表 : {}");
        return new Result(airQualityService.getStationInfoList(data));
    }
}

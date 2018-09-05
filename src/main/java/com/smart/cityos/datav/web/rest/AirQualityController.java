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
    @PostMapping(value = "/aqi7days")
    public Result get7DaysAQITrend(@RequestBody Map data) {
        log.debug("根据ID获取7天AQI柱状图数据列表 : {}");
        return new Result(airQualityService.get7DaysAQITrend(data));
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
        log.debug("获取站点状态列表 : {}");
        return new Result(airQualityService.getStationInfoList(data));
    }

    @ApiOperation("获取地图站点信息列表")
    @PostMapping(value = "/getStationInfoMap")
    public Result getStationInfoMap(@RequestBody Map data) {
        log.debug("获取地图站点信息列表 : {}");
        return new Result(airQualityService.getStationInfoMap(data));
    }

    @ApiOperation("获取地图站点信息")
    @PostMapping(value = "/getStationInfo")
    public Result getStationInfo(@RequestBody Map data) {
        log.debug("获取地图站点信息 : {}");
        return new Result(airQualityService.getStationInfo(data));
    }

    @ApiOperation("获取全市指标信息")
    @PostMapping(value = "/getStationTargetInfo")
    public Result getStationTargetInfo(@RequestBody Map data) {
        log.debug("获取全市指标信息 : {}");
        return new Result(airQualityService.getStationTargetInfo(data));
    }

    @ApiOperation("获取最新全市信息")
    @PostMapping(value = "/getCityStationInfo")
    public Result getCityStationInfo(@RequestBody Map data) {
        log.debug("获取最新全市信息 : {}");
        return new Result(airQualityService.getCityStationInfo(data));
    }

    @ApiOperation("获取最新更新时间点信息---已全市平均为准")
    @PostMapping(value = "/getTimePointInfo")
    public Result getTimePointInfo(@RequestBody Map data) {
        log.debug("获取最新更新时间点信息---已全市平均为准 : {}");
        return new Result(airQualityService.getTimePointInfo(data));
    }
}

package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.domain.model.AQI7days;
import com.smart.cityos.datav.domain.model.AQITrend;
import com.smart.cityos.datav.service.AirQualityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping(value = "/aqitrend")
    public List<AQITrend> get12HourAQITrend() {
        return airQualityService.get12HourAQITrend();
    }


    @ApiOperation("获取7天AQI柱状图数据列表")
    @GetMapping(value = "/aqi7days")
    public List<AQI7days> get7DaysAQITrend() {
        return airQualityService.get7DaysAQITrend();
    }

    @ApiOperation("根据日历获取降雨量数据列表")
    @GetMapping(value = "/rainfall")
    public List<Integer> getRainfall() {
        return airQualityService.getRainfall();
    }
}

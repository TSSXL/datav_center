package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.domain.Result;
import com.smart.cityos.datav.service.AirQualityService;
import com.smart.cityos.datav.service.ArbitrationService;
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
@RequestMapping("/api/arbitration")
@Slf4j
@AllArgsConstructor
@Api(value = "ArbitrationController", description = "仲裁委接口")
public class ArbitrationController {

    /**
     * 环保空气质量业务操作对象
     */
    @Autowired
    private ArbitrationService arbitrationService;

    @ApiOperation("获取今日统计情况")
    @PostMapping(value = "/getTodayCountByType")
    public Result getTodayCountByType(@RequestBody Map data) {
        log.debug("获取今日统计情况 : {}");
        return new Result(arbitrationService.getTodayCountByType(data));
    }

    @ApiOperation("获取月均指标统计")
    @PostMapping(value = "/getMonthPercent")
    public Result getMonthPercent(@RequestBody Map data) {
        log.debug("获取月均指标统计 : {}");
        return new Result(arbitrationService.getMonthPercent(data));
    }

    @ApiOperation("获取平均时间指标统计")
    @PostMapping(value = "/getAvgTime")
    public Result getAvgTime(@RequestBody Map data) {
        log.debug("获取平均时间指标统计 : {}");
        return new Result(arbitrationService.getAvgTime(data));
    }

    @ApiOperation("获取秘书统计")
    @PostMapping(value = "/MssCaseCount")
    public Result MssCaseCount(@RequestBody Map data) {
        log.debug("获取秘书统计 : {}");
        return new Result(arbitrationService.MssCaseCount(data));
    }

    @ApiOperation("获取秘书图表统计")
    @PostMapping(value = "/MssCaseBarCount")
    public Result MssCaseBarCount(@RequestBody Map data) {
        log.debug("获取秘书图表统计 : {}");
        return new Result(arbitrationService.MssCaseBarCount(data));
    }

    @ApiOperation("获取根据标的金额统计立案")
    @PostMapping(value = "/CountLasByAmonut")
    public Result CountLasByAmonut(@RequestBody Map data) {
        log.debug("获取根据标的金额统计立案 : {}");
        return new Result(arbitrationService.CountLasByAmonut(data));
    }

    @ApiOperation("获取根据案源统计立案")
    @PostMapping(value = "/CountLasByAYuan")
    public Result CountLasByAYuan(@RequestBody Map data) {
        log.debug("获取根据案源统计立案 : {}");
        return new Result(arbitrationService.CountLasByAYuan(data));
    }

    @ApiOperation("获取根据案源统计立案")
    @PostMapping(value = "/CountLasByAYou")
    public Result CountLasByAYou(@RequestBody Map data) {
        log.debug("获取根据案源统计立案 : {}");
        return new Result(arbitrationService.CountLasByAYou(data));
    }


}

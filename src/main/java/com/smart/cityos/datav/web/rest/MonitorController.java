package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.domain.Result;
import com.smart.cityos.datav.service.MonitorService;
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
@RequestMapping("/api/monitor")
@Slf4j
@AllArgsConstructor
@Api(value = "MonitorController", description = "监控平台接口")
public class MonitorController {

    /**
     * 监控操作对象
     */
    @Autowired
    private MonitorService monitorService;

    @ApiOperation("获取采集、共享任务状态")
    @PostMapping(value = "/getTaskStatus")
    public Result getTaskStatus(@RequestBody Map data) {
        log.debug("获取采集、共享任务状态 : {}");
        return new Result(monitorService.getTaskStatus(data));
    }

    @ApiOperation("获取监控采集订阅数据量")
    @PostMapping(value = "/getTaskDataCount")
    public Result getTaskDataCount(@RequestBody Map data) {
        log.debug("获取监控采集订阅数据量 : {}");
        return new Result(monitorService.getTaskDataCount(data));
    }

    @ApiOperation("获取监控采集订阅异常量")
    @PostMapping(value = "/getTaskErrorCount")
    public Result getTaskErrorCount(@RequestBody Map data) {
        log.debug("获取监控采集订阅异常量 : {}");
        return new Result(monitorService.getTaskErrorCount(data));
    }

    @ApiOperation("获取采集、订阅中心数据量显示详情")
    @PostMapping(value = "/getTaskDataInfoCount")
    public Result getTaskDataInfoCount(@RequestBody Map data) {
        log.debug("获取采集、订阅中心数据量显示详情 : {}");
        return new Result(monitorService.getTaskDataInfoCount(data));
    }

    @ApiOperation("获取数据库状态")
    @PostMapping(value = "/getDbStatus")
    public Result getDbStatus(@RequestBody Map data) {
        log.debug("获取数据库状态 : {}");
        return new Result(monitorService.getDbStatus(data));
    }

    @ApiOperation("根据类型获取监控状态")
    @PostMapping(value = "/getMonitorStatusByType")
    public Result getMonitorStatusByType(@RequestBody Map data) {
        log.debug("根据类型获取监控状态 : {}");
        return new Result(monitorService.getMonitorStatusByType(data));
    }

    @ApiOperation("根据应用获取监控状态")
    @PostMapping(value = "/getRefMonitorStatus")
    public Result getRefMonitorStatus(@RequestBody Map data) {
        log.debug("根据应用获取监控状态 : {}");
        return new Result(monitorService.getRefMonitorStatus(data));
    }


}

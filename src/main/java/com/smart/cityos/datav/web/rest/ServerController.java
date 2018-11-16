package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.domain.Result;
import com.smart.cityos.datav.service.MonitorService;
import com.smart.cityos.datav.service.ServerService;
import com.smart.cityos.datav.service.feign.config.ServerFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * server平台接口
 *
 * @author: mingcheong
 * @date Created in 2018-08-24
 */
@RestController
@RequestMapping("/api/server")
@Slf4j
@AllArgsConstructor
@Api(value = "ServerController", description = "server平台接口")
public class ServerController {

    /**
     * server对象
     */
    @Autowired
    private ServerService serverService;

    @ApiOperation("获取采集、共享任务总数")
    @PostMapping(value = "/collecttask/getTaskCount")
    public Result getTaskCount(@RequestBody Map data) {
        log.debug("获取采集、共享任务状态 : {}");
        return new Result(serverService.getTaskCount(data));
    }




}

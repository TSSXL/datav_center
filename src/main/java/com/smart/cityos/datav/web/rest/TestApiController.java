package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.domain.Result;
import com.smart.cityos.datav.service.TestApiService;
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
 * 污染源在线监测接口
 *
 * @author: mingcheong
 * @date Created in 2018-08-24
 */
@RestController
@RequestMapping("/api/textApi")
@Slf4j
@AllArgsConstructor
@Api(value = "TestApiController", description = "测试组件API连接接口")
public class TestApiController {


    @Autowired
    private TestApiService testApiService;

    /**
     * 根据类型返回值
     */
    @ApiOperation("根据类型返回值")
    @PostMapping(value = "/getTestApiInfo")
    public Result getTestApiInfo(@RequestBody Map data) {
        log.debug("根据类型返回值 : {}");
        return new Result(testApiService.getTestApiInfo(data));
    }

    /**
     * 根据类型返回值Map类型
     */
    @ApiOperation("根据类型返回值Map类型")
    @PostMapping(value = "/getTestApiMapInfo")
    public Result getTestApiMapInfo(@RequestBody Map data) {
        log.debug("根据类型返回值Map类型 : {}");
        return new Result(testApiService.getTestApiMapInfo(data));
    }



}

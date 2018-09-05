package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.domain.Result;
import com.smart.cityos.datav.service.AirQualityService;
import com.smart.cityos.datav.service.PollutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 污染源在线监测接口
 *
 * @author: mingcheong
 * @date Created in 2018-08-24
 */
@RestController
@RequestMapping("/api/pollution")
@Slf4j
@AllArgsConstructor
@Api(value = "pollutionController", description = "环保空气质量接口")
public class pollutionController {

    /**
     * 获取废水企业排口列表
     */
    @Autowired
    private PollutionService pollutionService;

    @ApiOperation("获取废水企业排口列表")
    @PostMapping(value = "/getFsQyInfoList")
    public Result getFsQyInfoList(@RequestBody Map data) {
        log.debug("获取废水企业排口列表 : {}");
        return new Result(pollutionService.getFsQyInfoList(data));
    }

    @ApiOperation("获取废气企业排口列表")
    @PostMapping(value = "/getGasQyInfoList")
    public Result getGasQyInfoList(@RequestBody Map data) {
        log.debug("获取废气企业排口列表 : {}");
        return new Result(pollutionService.getGasQyInfoList(data));
    }

    @ApiOperation("获取企业地理坐标列表")
    @PostMapping(value = "/getQyMapInfoList")
    public Result getQyMapInfoList(@RequestBody Map data) {
        log.debug("获取企业地理坐标列表 : {}");
        return new Result(pollutionService.getQyMapInfoList(data));
    }

    @ApiOperation("获取废气企业详细信息")
    @PostMapping(value = "/getGasQyInfo")
    public Result getGasQyInfo(@RequestBody Map data) {
        log.debug("获取废气企业详细信息 : {}");
        return new Result(pollutionService.getGasQyInfo(data));
    }

    @ApiOperation("获取废水企业详细信息")
    @PostMapping(value = "/getFsQyInfo")
    public Result getFsQyInfo(@RequestBody Map data) {
        log.debug("获取废水企业详细信息 : {}");
        return new Result(pollutionService.getFsQyInfo(data));
    }


}

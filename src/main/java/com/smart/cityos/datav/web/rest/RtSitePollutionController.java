package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.service.RtSitePollutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实时站点污染AQI接口
 *
 * @author: mingcheong
 * @date Created in 2018-08-24
 */
@RestController
@RequestMapping("/api/pollution")
@Slf4j
@AllArgsConstructor
@Api(value = "RtSitePollutionController", description = "实时站点污染AQI接口")
public class RtSitePollutionController {

    /**
     * 实时站点污染AQI业务操作对象
     */
    @Autowired
    private RtSitePollutionService rtSitePollutionService;

    @ApiOperation("获取实时站点污染AQI雷达图数据")
    @GetMapping(value = "/radar")
    public List<String> getChildIds() {

        return null;
    }
}

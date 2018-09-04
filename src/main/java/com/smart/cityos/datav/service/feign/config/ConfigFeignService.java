package com.smart.cityos.datav.service.feign.config;


import com.smart.cityos.datav.config.ApplicationProperties;
import com.smart.cityos.datav.domain.ExecuteQueryParam;
import com.smart.cityos.datav.domain.Result;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>title:配置中心</p>
 * <p>description:配置中心</p>
 *
 * @author:
 * @date Created in 2017-4-5
 */
@Service
@AllArgsConstructor
@Slf4j
public class ConfigFeignService {

    @Autowired
    private ApplicationProperties applicationProperties;

    private FeignServer createFeignServer() {
        return Feign.builder().encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignServer.class, applicationProperties.getSqlControllerUrl());
    }


    /**
     * 通过feign 通用sql查询mysql
     */
    public List<Map> executeQuery(Map executeQueryParam) {
        FeignServer feignServer = createFeignServer();
        List<Map> list = feignServer.executeQuery(executeQueryParam);
        return list;
    }

    /**
     * 通过feign 通用sql查询mysql
     */
    public List<Map> tableQuery(Map executeQueryParam) {
        FeignServer feignServer = createFeignServer();
        List<Map> list = feignServer.tableQuery(executeQueryParam);
        return list;
    }

    /**
     * 下发配置
     */
//  public Result dispatch(DeployOption deployOption) {
//    FeignServer feignServer = createFeignServer();
//    Result result = feignServer.dispatch(deployOption);
//    return result;
//  }

    /**
     * 获取是否人工审批
     */
//  public boolean getReviewInfo() {
//    boolean result;
//    try {
//      FeignServer feignServer = createFeignServer();
//      result = feignServer.getReviewInfo();
//    } catch (Exception e) {
//      log.error("获取审核方式配置失败，启用默认配置:{}",
//          applicationProperties.getDefaultReviewStatus() ? "系统自动审批" : "人工审批");
//      result = applicationProperties.getDefaultReviewStatus();
//    }
//    return result;
//  }
}

package com.smart.cityos.datav.service.feign.config;


import com.smart.cityos.datav.config.ApplicationProperties;
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
public class ServerFeignService {

    @Autowired
    private ApplicationProperties applicationProperties;


    private FeignServer createServerFeignServer() {
        return Feign.builder().encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(FeignServer.class, applicationProperties.getServerControllerUrl());
    }


    /**
     * 通过feign获取任务总数
     */
    public Map getTaskCount(Map executeQueryParam) {
        FeignServer feignServer = createServerFeignServer();
        Map list = feignServer.getTaskCount(executeQueryParam);
        return list;
    }

    /**
     * 通过feign获取任务总数
     */
    public Map getScreenCount(Map executeQueryParam) {
        FeignServer feignServer = createServerFeignServer();
        Map list = feignServer.getScreenCount(executeQueryParam);
        return list;
    }

    /**
     * 获取应用列表
     */
    public Map getAppsList(Map executeQueryParam) {
        FeignServer feignServer = createServerFeignServer();
        Map list = feignServer.getAppsList(executeQueryParam);
        return list;
    }


}

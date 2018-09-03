package com.smart.cityos.datav.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;


/**
 * <p>title:当前应用相关配置</p>
 * <p>description:当前应用相关配置</p>
 *
 * @author: yubj
 * @date Created in 2017-3-14
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    /**
     * cors跨域配置
     */
    @Getter
    private final CorsConfiguration cors = new CorsConfiguration();

    /**
     * 异步任务配置
     */
    @Getter
    private final Async async = new Async();


    /**
     * 可视化数据地址
     */
    @Getter
    @Setter
    private String sqlControllerUrl;


    public static class Async {

        @Getter
        @Setter
        private int corePoolSize = 2;

        @Getter
        @Setter
        private int maxPoolSize = 50;

        @Getter
        @Setter
        private int queueCapacity = 10000;
    }


}

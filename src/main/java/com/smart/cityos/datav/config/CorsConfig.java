package com.smart.cityos.datav.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * <p>title:跨域访问配置类</p>
 * <p>description:跨域访问配置类</p>
 *
 * @author: zhengkai
 * @date Created in 2018-01-04
 */
@Configuration
@Slf4j
@AllArgsConstructor
public class CorsConfig {

  /**
   * 跨域访问设置
   */
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = buildConfig();
    if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
      log.debug("注册跨域过滤器");
      source.registerCorsConfiguration("/api/**", config);
    }
    return new CorsFilter(source);
  }

  /**
   * 构建跨域配置对象
   *
   * @return 跨域配置对象
   */
  private CorsConfiguration buildConfig() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    return corsConfiguration;
  }
}

package com.smart.cityos.datav.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


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
   * 可视化数据地址
   */
  @Getter
  @Setter
  private String sqlControllerUrl;

}

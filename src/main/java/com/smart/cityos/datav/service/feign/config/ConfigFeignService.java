package com.smart.cityos.datav.service.feign.config;

//import com.cityos.rc.config.ApplicationProperties;
//import com.cityos.rc.domain.model.feign.config.ConfigOption;
//import com.cityos.rc.domain.model.feign.config.DeployOption;
//import com.cityos.rc.domain.model.feign.config.Result;
//import feign.Feign;
//import feign.jackson.JacksonDecoder;
//import feign.jackson.JacksonEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>title:配置中心</p>
 * <p>description:配置中心</p>
 *
 * @author:
 * @date Created in 2017-4-5
 */
@Service(value = "configFeignService")
@AllArgsConstructor
@Slf4j
public class ConfigFeignService {

//  private final ApplicationProperties applicationProperties;

//  private FeignServer createFeignServer() {
//    return Feign.builder().encoder(new JacksonEncoder())
//        .decoder(new JacksonDecoder())
//        .target(FeignServer.class, applicationProperties.getConfigUrl());
//  }

  /**
   * 生成配置文件
   */
//  public Result saveConfigs(ConfigOption configOption) {
//    FeignServer feignServer = createFeignServer();
//    Result result = feignServer.saveConfigs(configOption);
//    return result;
//  }

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

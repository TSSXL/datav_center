package com.smart.cityos.datav.service.feign.config;

//import com.cityos.rc.domain.model.feign.config.ConfigOption;
//import com.cityos.rc.domain.model.feign.config.DeployOption;
//import com.cityos.rc.domain.model.feign.config.Result;
//import feign.Headers;
//import feign.RequestLine;

/**
 * <p>title:配置中心</p>
 * <p>description:配置中心</p>
 *
 * @author:
 * @date Created in 2017-4-5
 */
public interface FeignServer {

  /**
   * 通过feign 远程提交配置选项到指定地址接口
   * @param configOption 配置选项
   * @return 返回接口处理后信息
   */
//  @RequestLine("POST /api/configs/saveconfig")
//  @Headers("Content-Type: application/json")
//  Result saveConfigs(ConfigOption configOption);

  /**
   * 通过feign远程调度部署选项到指定地址接口
   * @param deployOption 部署选项
   * @return 返回接口处理后信息
   */
//  @RequestLine("POST /api/configs/dispatch")
//  @Headers("Content-Type: application/json")
//  Result dispatch(DeployOption deployOption);

  /**
   * 通过feign 远程获取审核选项
   * @return 返回审核选项，true:自动；false:人工
   */
//  @RequestLine("GET /api/configs/reviewinfo")
//  boolean getReviewInfo();
}



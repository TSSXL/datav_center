package com.smart.cityos.datav.web.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-18
 * @modified By beckfun
 */
@RestController
@RequestMapping("/api/apiSource")
@Slf4j
@AllArgsConstructor
@Api(value = "ApiSourceController", description = "可视化设计接口")
public class ApiSourceController {

  @PostMapping("/")
  @ApiOperation("可视化设计列表接口")
  public Object getContent(){
return  null;
  }
}

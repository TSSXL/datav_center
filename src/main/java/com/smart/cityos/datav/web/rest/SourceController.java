package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.service.ISourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-26
 * @modified By beckfun
 */
@RestController
@RequestMapping("/api/source")
@Slf4j
@AllArgsConstructor
@Api(value = "SourceController", description = "数据接入之数据库方式")
public class SourceController {

  @Autowired
  private List<ISourceService> sourceServices;

  @ApiOperation("获取采集、共享任务总数")
  @GetMapping(value = "/{type}/{id}")
  public Object get(@PathVariable String type, @PathVariable String id) {
    Optional<ISourceService> sourceServiceOptional = sourceServices.stream()
        .filter(x -> x.getType().equalsIgnoreCase(type)).findFirst();

    if (sourceServiceOptional.isPresent()) {
      return sourceServiceOptional.get().getContent(id);
    }
    return null;
  }
}

package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.domain.ApiSource;
import com.smart.cityos.datav.domain.RestfulConfig;
import com.smart.cityos.datav.domain.model.ApiSourceModel;
import com.smart.cityos.datav.domain.model.QueryBody;
import com.smart.cityos.datav.domain.model.SourceInfo;
import com.smart.cityos.datav.service.ApiSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@Api(value = "ApiSourceController", description = "数据接入（Restful方式）")
public class ApiSourceController {

  @Autowired
  private ApiSourceService apiSourceService;

  @PostMapping("/test")
  @ApiOperation("")
  public Object test(@RequestBody RestfulConfig restfulConfig) {
    return apiSourceService.remoteRESTfulApi(restfulConfig);
  }

  @PostMapping("")
  @ApiOperation("")
  public void add(@RequestBody ApiSourceModel apiSourceModel) {
    RestfulConfig restfulConfig = apiSourceModel.getApiParam();
    if (restfulConfig == null || restfulConfig.getUrl() == null || restfulConfig.getUrl()
        .isEmpty()) {
      throw new RuntimeException("API地址不能为空");
    }
    apiSourceService.add(apiSourceModel);
  }

  @PutMapping("/{id}")
  @ApiOperation("")
  public void edit(@PathVariable String id, @RequestBody ApiSourceModel apiSourceModel) {
    apiSourceService.edit(id, apiSourceModel);
  }

  @PostMapping("/")
  @ApiOperation("分页获取组件信息")
  public Page<SourceInfo> list(@RequestParam Long currentPage, @RequestParam Long pageSize,
      @RequestParam String sort, @RequestParam Long order,
      @RequestBody List<QueryBody> queryBodies) {

    Sort sort1 = new Sort((order.equals(1) ? Direction.ASC : Direction.DESC),
        (sort.isEmpty() ? "_id" : sort));
    Pageable pageable = new PageRequest(currentPage.intValue() - 1, pageSize.intValue(), sort1);
    Optional<QueryBody> optionalQueryBody = queryBodies.stream()
        .filter(x -> "name".equals(x.getName())).findFirst();

    Page<ApiSource> apiSourcePage = apiSourceService
        .fetch(optionalQueryBody.isPresent() ? optionalQueryBody.get().getValue() : "", pageable);

    List<SourceInfo> apiSourceInfos = apiSourcePage.getContent().stream()
        .map(x -> new SourceInfo(x.getId(), x.getName(), x.getDesc())).collect(
            Collectors.toList());

    return new PageImpl<>(apiSourceInfos, pageable, apiSourcePage.getTotalElements());
  }
  @GetMapping("/{id}")
  @ApiOperation("")
  public ApiSource get(@PathVariable String id){
   return apiSourceService.get(id);
  }
}

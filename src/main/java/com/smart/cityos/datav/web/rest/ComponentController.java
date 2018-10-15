package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.domain.Component;
import com.smart.cityos.datav.domain.Result;
import com.smart.cityos.datav.domain.model.ComponentInfo;
import com.smart.cityos.datav.domain.model.ComponentModel;
import com.smart.cityos.datav.domain.model.QueryBody;
import com.smart.cityos.datav.service.ComponentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.DeleteMapping;
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
 * @date Created in 2018-09-30
 * @modified By beckfun
 */
@RestController
@RequestMapping("/api/component")
@Slf4j
@AllArgsConstructor
@Api(value = "ComponentController", description = "可视化组件接口")
public class ComponentController {

  @Autowired
  private ComponentService componentService;

  @PostMapping("/all")
  @ApiOperation("获取所有组件信息")
  public List<Component> all(@RequestBody Object queryBody) {
    return componentService.fetch(true);
  }

  @PostMapping("/list")
  @ApiOperation("分页获取组件信息")
  public Page<ComponentInfo> list(@RequestParam Long currentPage, @RequestParam Long pageSize,
      @RequestParam String sort, @RequestParam Long order,
      @RequestBody List<QueryBody> queryBodies) {
    Sort sort1 = new Sort((order.equals(1) ? Direction.ASC : Direction.DESC),
        (sort.isEmpty() ? "_id" : sort));
    Pageable pageable = new PageRequest(currentPage.intValue(), pageSize.intValue(), sort1);
    Optional<QueryBody> optionalQueryBody = queryBodies.stream()
        .filter(x -> "label".equals(x.getName())).findFirst();
    Page<Component> componentPage = componentService
        .fetch(optionalQueryBody.isPresent() ? optionalQueryBody.get().getValue() : "", pageable);

    List<ComponentInfo> componentInfos = componentPage.getContent().stream()
        .map(x -> new ComponentInfo(x.getId(), x.getLabel(), x.getName(), x.getType())).collect(
            Collectors.toList());

    return new PageImpl<>(componentInfos, pageable, componentPage.getTotalElements());
  }

  @PostMapping("")
  @ApiOperation("新增可视化组件")
  public void add(@RequestBody ComponentModel componentModel) {
    componentService.add(componentModel);
  }

  @PutMapping("/{id}")
  @ApiOperation("编辑可视化组件")
  public void edit(@PathVariable String id, @RequestBody ComponentModel componentModel) {
    componentService.edit(id, componentModel);
  }

  @DeleteMapping("/{id}")
  @ApiOperation("删除指定id可视化组件")
  public void delete(@PathVariable String id) {
    componentService.delete(id);
  }

  @GetMapping("/item/{id}")
  @ApiOperation("获取可视化组件详细信息")
  public Component get(@PathVariable String id) {
    return componentService.get(id);
  }

  @PostMapping("/getDayFormat")
  @ApiOperation("获取转换后的日期")
  public Result getDayFormat(@RequestBody Map data) {
    log.debug("获取转换后的日期 : {}");
    return new Result(componentService.getDayFormat(data));
  }

}

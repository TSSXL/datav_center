package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.domain.WsSource;
import com.smart.cityos.datav.domain.model.QueryBody;
import com.smart.cityos.datav.domain.model.WsSourceInfo;
import com.smart.cityos.datav.domain.model.WsSourceModel;
import com.smart.cityos.datav.service.WsSourceService;
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
 * @date Created in 2018-10-24
 * @modified By beckfun
 */
@RestController
@RequestMapping("/api/wsSource")
@Slf4j
@AllArgsConstructor
@Api(value = "WsSourceController", description = "数据接入（Restful方式）")
public class WsSourceController {
  @Autowired
  private WsSourceService wsSourceService;


  @PostMapping("")
  @ApiOperation("")
  public void add(@RequestBody WsSourceModel wsSourceModel) {
    wsSourceService.add(wsSourceModel);
  }

  @DeleteMapping("/{id}")
  @ApiOperation("")
  public void delete(@PathVariable String id) {
    wsSourceService.delete(id);
  }

  @PutMapping("/{id}")
  @ApiOperation("")
  public void edit(@PathVariable String id, @RequestBody WsSourceModel wsSourceModel) {
    wsSourceService.edit(id, wsSourceModel);
  }

  @PostMapping("/")
  @ApiOperation("分页获取组件信息")
  public Page<WsSourceInfo> list(@RequestParam Long pageNo, @RequestParam Long pageSize,
      @RequestParam String sort, @RequestParam Long order,
      @RequestBody List<QueryBody> queryBodies) {

    Sort sort1 = new Sort((order.equals(1) ? Direction.ASC : Direction.DESC),
        (sort.isEmpty() ? "_id" : sort));
    Pageable pageable = new PageRequest(pageNo.intValue() - 1, pageSize.intValue(), sort1);
    Optional<QueryBody> optionalQueryBody = queryBodies.stream()
        .filter(x -> "name".equals(x.getName())).findFirst();

    Page<WsSource> apiSourcePage = wsSourceService
        .fetch(optionalQueryBody.isPresent() ? optionalQueryBody.get().getValue() : "", pageable);

    List<WsSourceInfo> wsSourceInfos = apiSourcePage.getContent().stream()
        .map(x -> new WsSourceInfo(x.getId(), x.getName(), x.getDesc(), x.getAddr())).collect(
            Collectors.toList());

    return new PageImpl<>(wsSourceInfos, pageable, apiSourcePage.getTotalElements());
  }

  @GetMapping("/{id}")
  @ApiOperation("")
  public WsSource get(@PathVariable String id) {
    return wsSourceService.get(id);
  }
}

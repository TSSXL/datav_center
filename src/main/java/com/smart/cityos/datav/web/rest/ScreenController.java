package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.domain.Screen;
import com.smart.cityos.datav.domain.model.ScreenModel;
import com.smart.cityos.datav.service.ScreenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/api/screen")
@Slf4j
@AllArgsConstructor
@Api(value = "ScreenController", description = "可视化设计接口")
public class ScreenController {

  @Autowired
  private ScreenService screenService;

  @PostMapping("/list/cover")
  @ApiOperation("可视化设计列表接口")
  public Page<Screen> list(@RequestParam Long pageNo, @RequestParam Long pageSize,
      @RequestParam String sort, @RequestParam Long order) {
    Sort sort1 = new Sort((order.equals(1) ? Direction.ASC : Direction.DESC),
        (sort.isEmpty() ? "_id" : sort));
    Pageable pageable = new PageRequest(pageNo.intValue(), pageSize.intValue(), sort1);
    return screenService.fetch(pageable);
  }

  @PostMapping("")
  @ApiOperation("新增可视化设计")
  public void add(@RequestBody ScreenModel screenModel) {
    screenService.add(screenModel);
  }

  @PutMapping("/{id}")
  @ApiOperation("编辑可视化设计")
  public void edit(@PathVariable String id, @RequestBody ScreenModel screenModel) {
    screenService.edit(id, screenModel);
  }

  @DeleteMapping("/{id}")
  @ApiOperation("删除可视化设计")
  public void delete(@PathVariable String id) {
    screenService.delete(id);
  }

  @GetMapping("/{id}")
  public Screen get(@PathVariable String id) {
    return screenService.get(id);
  }
}

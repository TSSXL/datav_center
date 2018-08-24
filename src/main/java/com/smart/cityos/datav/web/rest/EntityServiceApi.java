package com.smart.cityos.datav.web.rest;

import com.smart.cityos.datav.domain.EntityPO;
import com.smart.cityos.datav.service.CommonEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.Assert;
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
 * 实体接口
 *
 * @author: zhengkai
 * @date Created in 2018-06-06
 */
@RestController
@RequestMapping("/api/entities")
@Slf4j
@AllArgsConstructor
@Api(value = "EntityServiceApi", description = "测试接口")
public class EntityServiceApi {

  /**
   * 实体业务操作对象
   */
  @Autowired
  private CommonEntityService commonEntityService;

  @ApiOperation("根据实体类型， 获取实体信息列表")
  @GetMapping(value = "/list")
  public List<EntityPO> getAll(@RequestParam String type) {
    Sort.Order orderParentId = new Sort.Order(Direction.DESC, "parentId");
    Sort.Order orderSort = new Sort.Order(Direction.ASC, "sort");

    List<Sort.Order> orders = new ArrayList<>();
    orders.add(orderParentId);
    orders.add(orderSort);
    Sort sort = new Sort(orders);

    return commonEntityService.getAll(type, sort);
  }

  @ApiOperation("根据实体ID， 获取实体信息")
  @GetMapping(value = "/{id}")
  public EntityPO getById(@PathVariable String id) {
    Assert.hasLength(id, "Entity id must not be null.");
    return commonEntityService.get(id);
  }

  @ApiOperation("根据实体ID集合， 获取实体信息集合")
  @PostMapping(value = "/ids")
  public List<EntityPO> getByIds(@RequestBody List<String> idList) {
    if (null == idList || idList.isEmpty()) {
      return null;
    }

    return commonEntityService.getByIds(idList);
  }

  @ApiOperation("根据实体ID， 获取实体全部子节点")
  @GetMapping(value = "/{id}/child")
  public List<String> getChildIds(@PathVariable String id, @RequestParam String type) {
    Assert.hasLength(id, "Entity id must not be null.");
    return commonEntityService.getChildIds(id, type);
  }

  @ApiOperation("创建实体信息")
  @PostMapping
  public EntityPO create(@RequestBody EntityPO entityPO) {
    return commonEntityService.create(entityPO);
  }

  @ApiOperation("更新实体信息")
  @PutMapping(value = "/{id}")
  public EntityPO update(@PathVariable String id, @RequestBody EntityPO entityPO) {
    Assert.hasLength(id, "entity id must not be null.");

    entityPO.setId(id);
    return commonEntityService.update(entityPO);
  }

  @ApiOperation("删除实体信息")
  @DeleteMapping
  public void delete(@RequestBody List<String> idList) {
    Assert.notEmpty(idList, "idList must not be empty.");
    commonEntityService.delete(idList);
  }

  @ApiOperation("批量更新实体信息")
  @PutMapping
  public List<EntityPO> update(@RequestBody List<EntityPO> entityPOList){
    Assert.notEmpty(entityPOList, "entity list must not be empty.");
    return commonEntityService.update(entityPOList);
  }
}

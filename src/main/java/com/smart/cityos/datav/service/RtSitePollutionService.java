package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.EntityPO;
import com.smart.cityos.datav.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 实时站点污染AQI业务类
 *
 * @author: mingcheong
 * @date Created in 2018-08-24
 */
@Service
public class RtSitePollutionService {

  /**
   * 实体持久化操作对象
   */
  @Autowired
  private EntityRepository entityRepository;

  /**
   * 根据实体Id，获取实体信息
   *
   * @param id 实体ID
   * @return 实体对象
   */
  public EntityPO get(String id) {
    return entityRepository.findOne(id);
  }

  /**
   * 获取指定ID实体的全部子实体ID集合
   *
   * @param id 指定实体ID
   * @param type 实体类型
   * @return 实体ID集合
   */
  public List<String> getChildIds(String id, String type) {
    List<String> idList = new ArrayList<>();
    getChildIds(idList, id, type);
    return idList;
  }

  /**
   * 创建实体
   *
   * @param entityPO 实体对象
   * @return 实体对象
   */
  public EntityPO create(EntityPO entityPO) {
    return entityRepository.insert(entityPO);
  }

  /**
   * 更新实体
   *
   * @param entityPO 实体对象
   * @return 实体对象
   */
  public EntityPO update(EntityPO entityPO) {
    return entityRepository.save(entityPO);
  }

  /**
   * 批量更新实体
   *
   * @return 实体集合
   */
  public List<EntityPO> update(List<EntityPO> entityPOList) {
    return entityRepository.save(entityPOList);
  }

  /**
   * 删除实体
   *
   * @param idList 实体ID集合
   */
  public void delete(List<String> idList) {
    entityRepository.deleteByIdIn(idList);
  }

  /**
   * 获取指定类型的全部实体，并排序
   *
   * @param type 实体类型
   * @param sort 排序
   * @return 指定类型的全部实体集合
   */
  public List<EntityPO> getAll(String type, Sort sort) {
    return entityRepository.findByType(type, sort);
  }

  /**
   *
   * @param idList
   * @return
   */
  public List<EntityPO> getByIds(List<String> idList) {
    return entityRepository.findByIdIn(idList);
  }

  /**
   * 获取子实体
   *
   * @param idList 实体ID集合
   * @param id 实体ID
   * @param type 实体类型
   */
  private void getChildIds(List<String> idList, String id, String type) {

    List<EntityPO> entityPOList = entityRepository.findByParentIdAndType(id, type);

    if (null == entityPOList || entityPOList.isEmpty()) {
      return;
    }

    for (EntityPO entityPO : entityPOList) {
      idList.add(entityPO.getId());
      getChildIds(idList, entityPO.getId(), type);
    }

  }

}

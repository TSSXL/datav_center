package com.smart.cityos.datav.repository;

import com.smart.cityos.datav.domain.EntityPO;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 实体持久化操作类
 *
 * @author: zhengkai
 * @date Created in 2018-06-06
 */
@Repository
public interface EntityRepository extends MongoRepository<EntityPO, String> {

  /**
   * 根据类型及id集合，获取实体集合
   *
   * @param type 实体类型
   * @param idList id集合
   * @return 实体集合
   */
  List<EntityPO> findByTypeAndIdIn(String type, List<String> idList);

  /**
   * 根据parentId及type获取实体信息
   *
   * @param parentId parentId
   * @param type 实体类型
   * @return 实体集合
   */
  List<EntityPO> findByParentIdAndType(String parentId, String type);

  /**
   * 根据实体ID集合，删除实体信息
   *
   * @param idList 实体ID集合
   */
  void deleteByIdIn(List<String> idList);

  /**
   * 根据类型获取实体集合
   *
   * @param type 类型
   * @param sort 排序
   * @return 实体集合
   */
  List<EntityPO> findByType(String type, Sort sort);

  /**
   * 根据实体ID集合，获取实体集合
   *
   * @param idList ID集合
   * @return 实体集合
   */
  List<EntityPO> findByIdIn(List<String> idList);
}
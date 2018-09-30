package com.smart.cityos.datav.repository;

import com.smart.cityos.datav.domain.Component;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-09-30
 * @modified By beckfun
 */
@Repository
public interface ComponentRepository extends MongoRepository<Component, String> {

  /**
   * Label 属性Label作为模糊搜索条件分页查询组件信息
   * @param label
   * @param pageable
   * @return
   */
  Page<Component> findAllByLabelLike(String label, Pageable pageable);

  /**
   * 属性 Active 作为查询条件
   * @param active
   * @return
   */
  List<Component> findAllByActive(boolean active);
}

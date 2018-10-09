package com.smart.cityos.datav.repository;

import com.smart.cityos.datav.domain.Screen;
import org.bson.types.ObjectId;
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
public interface ScreenRepository extends MongoRepository<Screen, String> {

  /**
   *
   * @param refApp
   * @param pageable
   * @return
   */
  Page<Screen> findAllByRefApp(ObjectId refApp, Pageable pageable);
}

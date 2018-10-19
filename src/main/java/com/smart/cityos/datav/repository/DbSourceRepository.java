package com.smart.cityos.datav.repository;

import com.smart.cityos.datav.domain.ApiSource;
import com.smart.cityos.datav.domain.DbSouce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-19
 * @modified By beckfun
 */
public interface DbSourceRepository extends MongoRepository<DbSouce, String> {
  Page<DbSouce> findAllByNameLike(String name, Pageable pageable);
}

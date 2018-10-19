package com.smart.cityos.datav.repository;

import com.smart.cityos.datav.domain.ApiSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-19
 * @modified By beckfun
 */
@Repository
public interface ApiSourceRepository extends MongoRepository<ApiSource, String> {

  Page<ApiSource> findAllByNameLike(String name, Pageable pageable);
}

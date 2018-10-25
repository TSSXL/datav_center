package com.smart.cityos.datav.repository;

import com.smart.cityos.datav.domain.WsSource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-24
 * @modified By beckfun
 */
@Repository
public interface WsSourceRepository extends MongoRepository<WsSource, String> {

}

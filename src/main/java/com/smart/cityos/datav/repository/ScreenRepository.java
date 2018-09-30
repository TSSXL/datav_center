package com.smart.cityos.datav.repository;

import com.smart.cityos.datav.domain.Screen;
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

}

package com.smart.cityos.datav.domain;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-11
 * @modified By beckfun
 */
@Getter
@Setter
public class DataSource {
  @Id
  private ObjectId id;
}

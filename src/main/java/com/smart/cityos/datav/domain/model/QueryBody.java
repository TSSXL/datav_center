package com.smart.cityos.datav.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-09-30
 * @modified By beckfun
 */
@Getter
@Setter
public class QueryBody {
  private String name;
  private  String operator;
  private String value;
}

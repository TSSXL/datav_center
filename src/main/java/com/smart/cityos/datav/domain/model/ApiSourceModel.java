package com.smart.cityos.datav.domain.model;

import com.smart.cityos.datav.domain.RestfulConfig;
import com.smart.cityos.datav.domain.TransScript;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-19
 * @modified By beckfun
 */
@Getter
@Setter
public class ApiSourceModel {
  private String name;
  private String desc;
  private RestfulConfig apiParam;
  private TransScript transScript;
}

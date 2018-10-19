package com.smart.cityos.datav.domain.model;

import lombok.AllArgsConstructor;
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
public class DbSourceInfo extends SourceInfo {

  private String instance;

  public DbSourceInfo(String id, String name, String desc, String instance) {
    super(id, name, desc);
    this.instance = instance;
  }
}

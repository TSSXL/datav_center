package com.smart.cityos.datav.domain.model;

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
public class DbSouceModel {

  private String type;
  private String addr;
  private String port;
  private String instance;
  private String characterSet;
  private String name;
  private String desc;
  private String loginUsername;
  private String loginPassword;
  private String sql;
}

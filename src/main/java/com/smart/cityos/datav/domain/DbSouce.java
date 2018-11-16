package com.smart.cityos.datav.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

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
public class DbSouce {

  @Id
  private String id;
  private String name;
  private String desc;
  private String type;
  private String addr;
  private String port;
  private String instance;
  private String characterSet;
  private String loginUsername;
  private String loginPassword;
  private String sql;
  private TransScript transScript;
}

package com.smart.cityos.datav.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-23
 * @modified By beckfun
 */
@Getter
@Setter
public class WsSource {

  @Id
  private String id;
  private String name;
  private String desc;
  private String addr;

}

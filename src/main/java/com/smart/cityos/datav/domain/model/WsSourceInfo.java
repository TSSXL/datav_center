package com.smart.cityos.datav.domain.model;

import lombok.Getter;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-24
 * @modified By beckfun
 */
@Getter
public class WsSourceInfo extends SourceInfo {
  private String addr;

  public WsSourceInfo(String id, String name, String desc,String addr) {
    super(id, name, desc);
    this.addr=addr;
  }
}

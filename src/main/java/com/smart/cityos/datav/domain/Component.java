package com.smart.cityos.datav.domain;

import java.util.List;
import lombok.Data;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-09-30
    * @modified By beckfun
    */
  @Data
  @Document(collection = "components")
  public class Component {
  /**
   * 唯一标识
   */
  @Id
  private String id;
  private String label;
  private String name;
  private List<String> type;
  private List<Object> icon;
  private String version;
  private Object option;
  private boolean active;
  private boolean selected;
  private String group;
  private String desc;
  private Long sort;

  /**
   * 创建时间
   */
  private Date createDate;
  /**
   * 修改时间
   */
  private Date modifyDate;
}

package com.smart.cityos.datav.domain;

import java.util.Date;
import java.util.List;
import lombok.Data;
import org.bson.types.ObjectId;
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
@Document(collection = "screens")
public class Screen {
  @Id
private String id;
  /**
   * 创建时间
   */
  private Date createDate;
  /**
   * 修改时间
   */
  private Date modifyDate;
  /**
   * 名称
   */
  private String name;
  /**
   * 描述
   */
  private String desc;
  /**
   * 标签
   */
  private List<String> tag;
  /**
   *  发布
   */
  private Object publish;
  /**
   * 版本
   */
  private String version;
  /**
   * 页面设置
   */
  private Object page;
  /**
   * 关联应用
   */
  private ObjectId refApp;
  /**
   * 组件
   */
  private List<Object> components;
}

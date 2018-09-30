package com.smart.cityos.datav.domain.model;

import java.sql.Date;
import java.util.List;
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
public class ScreenModel {

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
  private String refApp;
  /**
   * 组件
   */
  private List<Object> components;
}

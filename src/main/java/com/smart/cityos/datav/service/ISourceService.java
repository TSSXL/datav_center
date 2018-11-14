package com.smart.cityos.datav.service;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-26
 * @modified By beckfun
 */
public interface ISourceService {

  /**
   * 获取数据源服务类型
   * @return
   */
  String getType();

  /**
   * 获取数据源内容
   * @param id
   * @return
   */
  Object getContent(String id);
}

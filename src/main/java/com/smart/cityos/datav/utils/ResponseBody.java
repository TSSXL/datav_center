package com.smart.cityos.datav.utils;

import java.util.HashMap;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: yangqc
 * @date Created in 2017-3-24
 */
public class ResponseBody<K, V> extends HashMap<K, V> {

  private static final long serialVersionUID = -1854528247030921000L;

  public ResponseBody(K key, V value) {
    super();
    this.put(key, value);
  }
}

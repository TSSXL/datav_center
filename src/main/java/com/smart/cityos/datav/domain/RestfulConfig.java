package com.smart.cityos.datav.domain;

import com.smart.cityos.datav.domain.KeyValue;
import java.util.List;
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
public class RestfulConfig {
  private String url;
  private String method;
  private List<KeyValue> queryParams;
  private List<KeyValue> headers;
  private String body;
}

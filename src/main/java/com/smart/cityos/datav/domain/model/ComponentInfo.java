package com.smart.cityos.datav.domain.model;

import java.lang.reflect.Type;
import java.util.List;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class ComponentInfo {

  private String id;
  private String label;
  private String name;
  private List<String> type;
}

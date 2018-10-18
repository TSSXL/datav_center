package com.smart.cityos.datav.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-15
 * @modified By beckfun
 */
@Getter
@Setter
public class ApiSource {

  @Id
  private ObjectId id;
  private String url;
  private String name;
  private String desc;
  private String method;
  private List<KeyValue> Parameters;
  private List<KeyValue> header;
}

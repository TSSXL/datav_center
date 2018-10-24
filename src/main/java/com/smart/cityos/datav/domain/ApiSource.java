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
  private String id;
  private String name;
  private String desc;
  private RestfulConfig apiParam;
  private TransScript transScript;
}

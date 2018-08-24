package com.smart.cityos.datav.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 实体类
 *
 * @author: zhengkai
 * @date Created in 2018-06-06
 */
@Data
@Document(collection = "c_entities")
public class EntityPO {

  /**
   * 唯一标识
   */
  private String id;

  /**
   * 实体英文名称
   */
  @Field("name")
  private String name;

  /**
   * 实体英文名称
   */
  @Field("cn_name")
  private String cname;

  /**
   * 描述
   */
  @Field("description")
  private String description;

  /**
   * 创建时间
   */
  @Field("create_time")
  private Date createTime;

  /**
   * 创建账号节点ID
   */
  @Field("creator_id")
  private String creatorId;

  /**
   * 最后更新时间
   */
  @Field("last_modify_time")
  private Date lastModifyTime;

  /**
   * 最后更新账号节点Id
   */
  @Field("last_modifier_id")
  private String lastModifierId;

  /**
   * 管理者账号节点Id
   */
  @Field("manager_id")
  private String managerId;

  /**
   * 分类码
   */
  @Field("category")
  private String category;

  /**
   * 平台标签
   */
  @Field("platform_tags")
  private String[] platformTags;

  /**
   * 系统标签
   */
  @Field("app_tags")
  private String[] appTags;

  /**
   * 用户使用标签
   */
  @Field("user_tags")
  private String[] userTags;

  /**
   * 父实体ID
   */
  @Field("parent_id")
  private String parentId;

  /**
   * 排序
   */
  @Field("sort")
  private Integer sort;

  /**
   * 电话
   */
  @Field("telephone")
  private String telephone;

  /**
   * 邮箱
   */
  @Field("email")
  private String email;

  /**
   * 地址
   */
  @Field("address")
  private String address;

  /**
   * 具体类型
   */
  @Field("type")
  private String type;

  /**
   * 扩展字段
   */
  @Field("extended_fields")
  private Map<String, Object> extendedFields = new HashMap<>();

  /**
   * 实体类型
   */
  public class EntityType {

    /**
     * 个人实体类型
     */
    public static final String ENTITY_TYPE_PERSONAL = "personal";

    /**
     * 企业实体类型
     */
    public static final String ENTITY_TYPE_ENTERPRISE = "enterprise";

    /**
     * 政府实体类型
     */
    public static final String ENTITY_TYPE_GOVERNMENT = "government";
  }

}

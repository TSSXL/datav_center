package com.smart.cityos.datav.domain.model;

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
public class ComponentModel {
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
}

package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.Component;
import com.smart.cityos.datav.domain.model.ComponentModel;
import com.smart.cityos.datav.repository.ComponentRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sun.util.calendar.BaseCalendar.Date;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-09-30
 * @modified By beckfun
 */
@Service
public class ComponentService {

  @Autowired
  private ComponentRepository componentRepository;

  public Page<Component> fetch(String label, Pageable pageable) {
    return label == null || label.isEmpty() ? componentRepository.findAll(pageable)
        : componentRepository.findAllByLabelLike(label, pageable);
  }

  public List<Component> fetch(boolean active){
    return componentRepository.findAllByActive(active);
  }

  public void add(ComponentModel componentModel) {
    Component component = new Component();
    component.setDesc(componentModel.getDesc());
    component.setGroup(componentModel.getGroup());
    component.setIcon(componentModel.getIcon());
    component.setLabel(componentModel.getLabel());
    component.setName(componentModel.getName());
    component.setSelected(componentModel.isSelected());
    component.setSort(componentModel.getSort());
    component.setType(componentModel.getType());
    component.setVersion(componentModel.getVersion());
    component.setOption(componentModel.getOption());
    component.setActive(componentModel.isActive());
    component.setCreateDate(java.util.Calendar.getInstance().getTime());
    component.setModifyDate(java.util.Calendar.getInstance().getTime());
    componentRepository.save(component);
  }

  public void edit(String id, ComponentModel componentModel) {
    Component component = componentRepository.findOne(id);
    component.setDesc(componentModel.getDesc());
    component.setGroup(componentModel.getGroup());
    component.setIcon(componentModel.getIcon());
    component.setLabel(componentModel.getLabel());
    component.setName(componentModel.getName());
    component.setSelected(componentModel.isSelected());
    component.setSort(componentModel.getSort());
    component.setType(componentModel.getType());
    component.setVersion(componentModel.getVersion());
    component.setOption(componentModel.getOption());
    component.setActive(componentModel.isActive());
    component.setModifyDate(java.util.Calendar.getInstance().getTime());
    componentRepository.save(component);
  }

  public void delete(String id){
    componentRepository.delete(id);
  }

  public  Component get(String id){
    return  componentRepository.findOne(id);
  }

  public List<Map> getDayFormat(Map map){
    List<Map> result=new ArrayList<>();
    SimpleDateFormat dFormat = new SimpleDateFormat(map.get("format").toString());
    Map dMap=new HashMap();
    dMap.put("value",dFormat.format(new java.util.Date()));

    result.add(dMap);

    return result;
  }
}

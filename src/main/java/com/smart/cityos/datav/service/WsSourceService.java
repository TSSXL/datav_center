package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.WsSource;
import com.smart.cityos.datav.domain.model.WsSourceModel;
import com.smart.cityos.datav.repository.WsSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-24
 * @modified By beckfun
 */
@Service
public class WsSourceService {

  @Autowired
  private WsSourceRepository wsSourceRepository;

  public WsSource add(WsSourceModel wsSourceModel) {
    WsSource wsSource=new WsSource();
    wsSource.setAddr(wsSourceModel.getAddr());
    wsSource.setDesc(wsSourceModel.getDesc());
    wsSource.setName(wsSourceModel.getName());
    wsSourceRepository.save(wsSource);
    return wsSource;
  }

  public void delete(String id) {
    wsSourceRepository.delete(id);
  }

  public WsSource edit(String id, WsSourceModel wsSourceModel) {
    WsSource wsSource=wsSourceRepository.findOne(id);
    wsSource.setAddr(wsSourceModel.getAddr());
    wsSource.setDesc(wsSourceModel.getDesc());
    wsSource.setName(wsSourceModel.getName());
    wsSourceRepository.save(wsSource);
    return wsSource;
  }

  public Page<WsSource> fetch(String name,Pageable pageable) {
    return wsSourceRepository.findAll(pageable);
  }
  public WsSource get(String id){
    return wsSourceRepository.findOne(id);
  }
}

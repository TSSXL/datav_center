package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.ApiSource;
import com.smart.cityos.datav.domain.DbSouce;
import com.smart.cityos.datav.domain.model.DbSouceModel;
import com.smart.cityos.datav.repository.DbSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-10-19
 * @modified By beckfun
 */
@Service
public class DbSourceService {
  @Autowired
  private DbSourceRepository dbSourceRepository;
  public void add(DbSouceModel dbSouceModel){
    DbSouce dbSouce=new DbSouce();
    dbSouce.setType(dbSouceModel.getType());
    dbSouce.setAddr(dbSouceModel.getAddr());
    dbSouce.setPort(dbSouceModel.getPort());
    dbSouce.setInstance(dbSouceModel.getInstance());
    dbSouce.setCharacterSet(dbSouceModel.getCharacterSet());
    dbSouce.setName(dbSouceModel.getName());
    dbSouce.setDesc(dbSouceModel.getDesc());
    dbSouce.setLoginUsername(dbSouceModel.getLoginUsername());
    dbSouce.setLoginPassword(dbSouceModel.getLoginPassword());
    dbSourceRepository.save(dbSouce);
  }
  public void delete(String id){
    dbSourceRepository.delete(id);
  }
  public void edit(String id,DbSouceModel dbSouceModel){
    DbSouce dbSouce=dbSourceRepository.findOne(id);
    dbSouce.setType(dbSouceModel.getType());
    dbSouce.setAddr(dbSouceModel.getAddr());
    dbSouce.setPort(dbSouceModel.getPort());
    dbSouce.setInstance(dbSouceModel.getInstance());
    dbSouce.setCharacterSet(dbSouceModel.getCharacterSet());
    dbSouce.setName(dbSouceModel.getName());
    dbSouce.setDesc(dbSouceModel.getDesc());
    dbSouce.setLoginUsername(dbSouceModel.getLoginUsername());
    dbSouce.setLoginPassword(dbSouceModel.getLoginPassword());
    dbSourceRepository.save(dbSouce);
  }


  public Page<DbSouce> fetch(String name, Pageable pageable) {
    return name == null || name.isEmpty() ? dbSourceRepository.findAll(pageable)
        : dbSourceRepository.findAllByNameLike(name, pageable);
  }

  public DbSouce get(String id) {
    return dbSourceRepository.findOne(id);
  }
}

package com.smart.cityos.datav.service;

import com.smart.cityos.datav.domain.Screen;
import com.smart.cityos.datav.domain.model.ScreenModel;
import com.smart.cityos.datav.domain.model.ScreenQueryBody;
import com.smart.cityos.datav.repository.ScreenRepository;

import java.util.Calendar;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author: beckfun
 * @date Created in 2018-09-30
 * @modified By beckfun
 */
@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

    public Page<Screen> fetch(String name,String refApp, Pageable pageable) {
        if (name.isEmpty() && refApp.isEmpty()) {
            return screenRepository.findAll(pageable);
        } else {
            return screenRepository
                .findAllByRefAppAndNameLike(new ObjectId(refApp), name, pageable);
        }
    }

    public Screen add(ScreenModel screenModel) {

        Screen screen = new Screen();
        screen.setComponents(screenModel.getComponents());
        screen.setDesc(screenModel.getDesc());
        screen.setName(screenModel.getName());
        screen.setRefApp(screenModel.getRefApp());
        screen.setTag(screenModel.getTag());
        screen.setVersion(screenModel.getVersion());
        screen.setPage(screenModel.getPage());
        screen.setPublish(screen.getPublish());
        screen.setCreateDate(Calendar.getInstance().getTime());
        screen.setModifyDate(Calendar.getInstance().getTime());

        screenRepository.save(screen);
        return screen;
    }


    public void edit(String id, ScreenModel screenModel) {
        Screen screen = screenRepository.findOne(id);

        screen.setComponents(screenModel.getComponents());
        screen.setDesc(screenModel.getDesc());
        screen.setName(screenModel.getName());
        screen.setRefApp(screenModel.getRefApp());
        screen.setTag(screenModel.getTag());
        screen.setVersion(screenModel.getVersion());
        screen.setPage(screenModel.getPage());
        screen.setPublish(screen.getPublish());
        screen.setModifyDate(Calendar.getInstance().getTime());
        screenRepository.save(screen);
    }

    public Screen get(String id) {
        return screenRepository.findOne(id);
    }

    public void delete(String id) {
        screenRepository.delete(id);
    }

    public String copy(String id) {
        Screen screen = screenRepository.findOne(id);
        Screen screenShadow = (Screen) screen.clone();
        screenShadow.setId(null);
        screenRepository.save(screenShadow);
        return screenShadow.getId();
    }
}

package org.plumeframework.demo.notes.controllers;

import java.util.Map;

import org.plumeframework.Plume;
import org.plumeframework.def.ComponentDef;
import org.plumeframework.instance.Component;
import org.plumeframework.system.Annotations.Controller;
import org.plumeframework.system.Annotations.Key;
import org.plumeframework.system.Annotations.PlumeEnabled;

import org.plumeframework.demo.notes.DataStore;
import org.plumeframework.demo.notes.Note;

import com.google.common.collect.Maps;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

@Controller
public class NoteViewController {

    @PlumeEnabled
    public static Component deleteNote(@Key("id")Long id, @Key("sort")String sort) throws Exception {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        noteDao.deleteById(id);

        Map<String, Object> listAttributes = Maps.newHashMap();
        listAttributes.put("sort", sort);
        return Plume.getInstanceService().getInstance("plumenote:noteList", ComponentDef.class, listAttributes);
    }
}

package org.auraframework.demo.notes.controllers;

import java.util.Map;

import org.auraframework.Aura;
import org.auraframework.def.ComponentDef;
import org.auraframework.demo.notes.DataStore;
import org.auraframework.demo.notes.Note;
import org.auraframework.instance.Component;
import org.auraframework.system.Annotations.AuraEnabled;
import org.auraframework.system.Annotations.Controller;
import org.auraframework.system.Annotations.Key;

import com.google.common.collect.Maps;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

@Controller
public class NoteViewController {

    @AuraEnabled
    public static Component deleteNote(@Key("id")Long id, @Key("sort")String sort) throws Exception {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        noteDao.deleteById(id);

        Map<String, Object> listAttributes = Maps.newHashMap();
        listAttributes.put("sort", sort);
        return Aura.getInstanceService().getInstance("auranote:noteList", ComponentDef.class, listAttributes);
    }
}

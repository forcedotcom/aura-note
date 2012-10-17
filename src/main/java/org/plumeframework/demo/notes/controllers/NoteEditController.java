package org.plumeframework.demo.notes.controllers;

import java.math.BigDecimal;
import java.util.Map;

import org.plumeframework.Plume;
import org.plumeframework.def.ComponentDef;
import org.plumeframework.demo.notes.DataStore;
import org.plumeframework.demo.notes.Note;
import org.plumeframework.instance.Component;
import org.plumeframework.system.Annotations.Controller;
import org.plumeframework.system.Annotations.Key;
import org.plumeframework.system.Annotations.PlumeEnabled;

import com.google.common.collect.Maps;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

@Controller
public class NoteEditController {

    @PlumeEnabled
    public static Component saveNote(@Key("id")Long id,
                                     @Key("title")String title,
                                     @Key("body")String body,
                                     @Key("sort")String sort,
                                     @Key("latitude")BigDecimal latitude,
                                     @Key("longitude")BigDecimal longitude) throws Exception {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        Note note;

        if(id != null){
            note = noteDao.queryForId(id);
        }else{
            note = new Note();
        }
        
        if (title == null) {
            title = "";
        }

        if (body == null) { 
            body = ""; 
        }

        note.setTitle(title);
        note.setBody(body);
        if (latitude != null && longitude != null) {
            note.setLatitude(latitude.doubleValue());
            note.setLongitude(longitude.doubleValue());
        }

        if (id != null) {
            noteDao.update(note);
        } else {
            noteDao.create(note);
        }
        Map<String, Object> listAttributes = Maps.newHashMap();
        listAttributes.put("sort", sort);

        return Plume.getInstanceService().getInstance("plumenote:noteList", ComponentDef.class, listAttributes);
    }
}

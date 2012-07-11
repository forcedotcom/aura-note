package org.lumenframework.demo.notes.controllers;

import java.math.BigDecimal;
import java.util.Map;

import lumen.Lumen;
import lumen.def.ComponentDef;
import lumen.instance.Component;
import lumen.system.Annotations.Controller;
import lumen.system.Annotations.Key;
import lumen.system.Annotations.LumenEnabled;

import org.lumenframework.demo.notes.DataStore;
import org.lumenframework.demo.notes.Note;

import com.google.common.collect.Maps;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

@Controller
public class NoteEditController {
	
	@LumenEnabled
    public static Component saveNote(@Key("id")Long id,
    								 @Key("title")String title,
    								 @Key("body")String body,
    								 @Key("sort")String sort,
    								 @Key("latitude")BigDecimal latitude,
    								 @Key("longitude")BigDecimal longitude) throws Exception {
		Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);

		if (body == null) { 
			body = ""; 
			}
		
		Note note;
		if(id != null){
			note = noteDao.queryForId(id);
		}else{
			note = new Note();
		}
		note.setTitle(title);
		note.setBody(body);
		if(latitude != null && longitude != null){
			note.setLatitude(latitude.doubleValue());
			note.setLongitude(longitude.doubleValue());
		}

		if(id != null){
			noteDao.update(note);
		}else{
			noteDao.create(note);
		}
		Map<String, Object> listAttributes = Maps.newHashMap();
		listAttributes.put("sort", sort);
		
		return Lumen.getInstanceService().getInstance("lumennote:noteList", ComponentDef.class, listAttributes);
	}
}

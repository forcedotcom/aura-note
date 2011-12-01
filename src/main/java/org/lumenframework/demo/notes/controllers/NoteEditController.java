package org.lumenframework.demo.notes.controllers;

import lumen.system.Annotations.Controller;
import lumen.system.Annotations.Key;
import lumen.system.Annotations.LumenEnabled;

import org.lumenframework.demo.notes.DataStore;
import org.lumenframework.demo.notes.Note;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

@Controller
public class NoteEditController {
	@LumenEnabled
    public static Note saveNote(@Key("id")Long id, @Key("title")String title, @Key("body")String body) throws Exception {
		Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);

		Note note;
		if(id != null){
			note = noteDao.queryForId(id);
		}else{
			note = new Note();
		}
		note.setTitle(title);
		note.setBody(body);

		if(id != null){
			noteDao.update(note);
		}else{
			noteDao.create(note);
		}
		return note;
	}
}

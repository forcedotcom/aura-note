package org.lumenframework.demo.notes.controllers;

import lumen.system.Annotations.Controller;
import lumen.system.Annotations.Key;
import lumen.system.Annotations.LumenEnabled;

import org.lumenframework.demo.notes.DataStore;
import org.lumenframework.demo.notes.Note;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

@Controller
public class NoteListController {
	@LumenEnabled
    public static void createNoteAction(@Key("title")String title, @Key("body")String body) throws Exception {
		Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);

		Note note = new Note();
		note.setTitle(title);
		note.setBody(body);

		noteDao.create(note);
	}
}

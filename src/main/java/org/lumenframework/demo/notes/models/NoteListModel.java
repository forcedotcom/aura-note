package org.lumenframework.demo.notes.models;

import java.util.List;

import lumen.system.Annotations.LumenEnabled;
import lumen.system.Annotations.Model;

import org.lumenframework.demo.notes.HelloNotes;
import org.lumenframework.demo.notes.Note;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

@Model
public class NoteListModel {
	public NoteListModel() throws Exception {
		ConnectionSource connection = HelloNotes.getConnection();
		Dao<Note, Long> noteDao = DaoManager.createDao(connection, Note.class);
		
		notes = noteDao.queryForAll();
	
		notes.add(new Note("Sample Note", "Just a simple note to let you know <h1>Lumen</h1> loves you!"));
	}
	
	@LumenEnabled
	public List<Note> getNotes() {
		return notes;
	}
	
	private final List<Note> notes;
}

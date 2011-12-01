package org.lumenframework.demo.notes.models;

import java.util.List;

import lumen.system.Annotations.LumenEnabled;
import lumen.system.Annotations.Model;

import org.lumenframework.demo.notes.DataStore;
import org.lumenframework.demo.notes.Note;

import com.j256.ormlite.dao.Dao;

@Model
public class NoteListModel {
	
	private static DataStore dataStore = DataStore.getInstance();
	private final List<Note> notes;
	
	public NoteListModel() throws Exception {
		Dao<Note, Long> noteDao = dataStore.getNoteDao();
		
		notes = noteDao.queryBuilder().orderBy("createdOn", false).query();
			
		if (notes.isEmpty()) {
			notes.add(new Note("Sample Note", "Just a simple note to let you know <h1>Lumen</h1> loves you!"));
		}
	}
	
	@LumenEnabled
	public List<Note> getNotes() {
		return notes;
	}
}

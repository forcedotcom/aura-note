package org.lumenframework.demo.notes.models;

import java.util.List;

import lumen.Lumen;
import lumen.instance.BaseComponent;
import lumen.system.Annotations.LumenEnabled;
import lumen.system.Annotations.Model;
import org.lumenframework.demo.notes.Note;
import org.lumenframework.demo.notes.persist.PersistableNoteH2Impl;


enum SortCol {
	title, createdOn
};

enum SortDir {
	asc, desc
}

@Model
public class NoteListModel {

	private List<Note> notes;

	public NoteListModel() throws Exception {
		BaseComponent<?, ?> cmp = Lumen.getContextService().getCurrentContext().getCurrentComponent();

		PersistableNoteH2Impl persistImpl = new PersistableNoteH2Impl();
		notes = persistImpl.getNoteList(cmp);

		if (notes.isEmpty()) {
			notes.add(new Note("Sample Note", "Just a simple note to let you know <h1>Lumen</h1> loves you!"));
		}
	}

	@LumenEnabled
	public List<Note> getNotes() {
		return notes;
	}
}

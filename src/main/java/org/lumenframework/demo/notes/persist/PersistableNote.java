package org.lumenframework.demo.notes.persist;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import lumen.instance.BaseComponent;

import org.lumenframework.demo.notes.Note;

public interface PersistableNote {

	public List<Note> getNoteList(BaseComponent<?, ?> cmp) throws Exception;

	public void deleteById(Long id) throws Exception;

	public void saveNote(Long id, String title, String body, BigDecimal latitude, BigDecimal longitude) throws SQLException;


}

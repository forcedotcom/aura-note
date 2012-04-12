package org.lumenframework.demo.notes.persist;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import lumen.instance.BaseComponent;
import lumen.util.TextUtil;

import org.lumenframework.demo.notes.Note;


import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;

enum SortCol {
	title, createdOn
};

enum SortDir {
	asc, desc
}

public class PersistableNoteH2Impl implements PersistableNote {
	public List<Note> getNoteList(BaseComponent<?, ?> cmp) throws Exception {
		List<Note> notes;
		DataStore dataStore = DataStore.getInstance();

		Dao<Note, Long> noteDao = dataStore.getNoteDao();

		List<String> sortSplit = TextUtil.splitSimple(".", (String) cmp.getAttributes().getValue("sort"));

		SortCol sortCol = SortCol.createdOn;
		SortDir sortDir = SortDir.desc;

		if (sortSplit != null) {
			sortCol = SortCol.valueOf(sortSplit.get(0));
			sortDir = SortDir.valueOf(sortSplit.get(1));
		}

		String query = (String) cmp.getAttributes().getValue("query");
		QueryBuilder<Note, Long> qb = noteDao.queryBuilder();
		if (!TextUtil.isNullEmptyOrWhitespace(query)) {
			List<Long> ids = Lists.newArrayList();
			GenericRawResults<String[]> searchResults = noteDao.queryRaw("SELECT KEYS FROM FT_SEARCH_DATA(?,0,0)", query);
			try {
				for (String[] row : searchResults) {
					ids.add(Long.parseLong(TextUtil.replaceAllRegex(row[0], "[()]", "")));
				}
			} finally {
				searchResults.close();
			}

			qb.setWhere(qb.where().in("id", ids));
		}

		qb.orderBy(sortCol.name(), sortDir == SortDir.asc);
		qb.limit(100L);

		notes = qb.query();
		return notes;
	}

	public void deleteById(Long id) throws Exception {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        noteDao.deleteById(id);
	}

	public void saveNote(Long id, String title, String body, BigDecimal latitude, BigDecimal longitude)
		throws SQLException
	{
		Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);

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
	}
}

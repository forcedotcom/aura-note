package org.plumeframework.demo.notes.models;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.plumeframework.Plume;
import org.plumeframework.demo.notes.DataStore;
import org.plumeframework.demo.notes.Note;
import org.plumeframework.instance.BaseComponent;
import org.plumeframework.system.Annotations.Model;
import org.plumeframework.system.Annotations.PlumeEnabled;
import org.plumeframework.util.PlumeTextUtil;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;

enum SortCol {
	title, createdOn
};

enum SortDir {
	asc, desc
}


@Model
public class NoteListModel {

	private static DataStore dataStore = DataStore.getInstance();
	private List<Note> notes;

	private static String replaceAllRegex(String source, String pattern, String replacement) {
	    if (source == null)
	          return null;
	    Pattern regex = Pattern.compile(pattern);
	    Matcher matcher = regex.matcher(source);
	    return matcher.replaceAll(replacement);
	}
	
	public NoteListModel() throws Exception {
		Dao<Note, Long> noteDao = dataStore.getNoteDao();

		BaseComponent<?, ?> cmp = Plume.getContextService().getCurrentContext().getCurrentComponent();

		List<String> sortSplit = PlumeTextUtil.splitSimple(".", (String) cmp.getAttributes().getValue("sort"));

		SortCol sortCol = SortCol.createdOn;
		SortDir sortDir = SortDir.desc;

		if (sortSplit != null) {
			sortCol = SortCol.valueOf(sortSplit.get(0));
			sortDir = SortDir.valueOf(sortSplit.get(1));
		}

		String query = (String) cmp.getAttributes().getValue("query");
		QueryBuilder<Note, Long> qb = noteDao.queryBuilder();
		if (!PlumeTextUtil.isNullEmptyOrWhitespace(query)) {
			List<Long> ids = Lists.newArrayList();
			GenericRawResults<String[]> searchResults = noteDao.queryRaw("SELECT KEYS FROM FT_SEARCH_DATA(?,0,0)", query);
			try {
				for (String[] row : searchResults) {
					ids.add(Long.parseLong(replaceAllRegex(row[0], "[()]", "")));
				}
			} finally {
				searchResults.close();
			}

			qb.setWhere(qb.where().in("id", ids));
		}
		
		qb.orderBy(sortCol.name(), sortDir == SortDir.asc);
		qb.limit(100L);

		notes = qb.query();

		if (notes.isEmpty()) {
			notes.add(new Note("Sample Note", "Just a simple note to let you know <h1>Plume</h1> loves you!"));
		}
	}
	
	

	@PlumeEnabled
	public List<Note> getNotes() {
		return notes;
	}
}

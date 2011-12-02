package org.lumenframework.demo.notes.models;

import java.util.List;

import lumen.Lumen;
import lumen.instance.BaseComponent;
import lumen.system.Annotations.LumenEnabled;
import lumen.system.Annotations.Model;
import lumen.util.TextUtil;

import org.lumenframework.demo.notes.DataStore;
import org.lumenframework.demo.notes.Note;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;

enum SortCol{
	title,
	createdOn	
};

enum SortDir{
	asc,
	desc
}

@Model
public class NoteListModel {
	
	private static DataStore dataStore = DataStore.getInstance();
	private  List<Note> notes;
	
	public NoteListModel() throws Exception {
		Dao<Note, Long> noteDao = dataStore.getNoteDao();
		
		BaseComponent<?,?> cmp = Lumen.getContextService().getCurrentContext().getCurrentComponent();
		

		List<String> sortSplit = TextUtil.splitSimple(".",(String)cmp.getAttributes().getValue("sort"));
		SortCol sortCol = SortCol.valueOf(sortSplit.get(0));
		SortDir sortDir = SortDir.valueOf(sortSplit.get(1));
		
		String query = (String)cmp.getAttributes().getValue("query");
		QueryBuilder<Note, Long> qb = noteDao.queryBuilder();
		if(!TextUtil.isNullEmptyOrWhitespace(query)){
			List<Long> ids = Lists.newArrayList();
			GenericRawResults<String[]> searchResults = noteDao.queryRaw("SELECT KEYS FROM FT_SEARCH_DATA(?,0,0)", query);
			try{
				for(String[] row : searchResults){
					ids.add(Long.parseLong(TextUtil.replaceAllRegex(row[0], "[()]", "")));
				}
			}finally{
				searchResults.close();
			}
			
			qb.setWhere(qb.where().in("id", ids));
		}
		qb.orderBy(sortCol.name(), sortDir == SortDir.asc);
		qb.limit(10L);
		
		notes = qb.query();
				
		if (notes.isEmpty()) {
			notes.add(new Note("Sample Note", "Just a simple note to let you know <h1>Lumen</h1> loves you!"));
		}
	}
	
	@LumenEnabled
	public List<Note> getNotes() {
		return notes;
	}
}

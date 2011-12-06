package org.lumenframework.demo.notes.controllers;

import java.util.List;

import lumen.system.Annotations.Controller;
import lumen.system.Annotations.Key;
import lumen.system.Annotations.LumenEnabled;
import lumen.util.TextUtil;

import org.lumenframework.demo.notes.DataStore;
import org.lumenframework.demo.notes.Note;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.*;

@Controller
public class TestNoteListController {

    @LumenEnabled
    public static void deleteNotesByKey(@Key("key") String key) throws Exception {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        List<Long> ids = Lists.newArrayList();
        GenericRawResults<String[]> searchResults = noteDao.queryRaw("SELECT KEYS FROM FT_SEARCH_DATA(?,0,0)", key);
        try {
            for (String[] row : searchResults) {
                ids.add(Long.parseLong(TextUtil.replaceAllRegex(row[0], "[()]", "")));
            }
        } finally {
            searchResults.close();
        }
        noteDao.deleteIds(ids);
    }
}

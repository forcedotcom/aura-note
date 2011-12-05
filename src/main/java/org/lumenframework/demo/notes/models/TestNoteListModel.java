package org.lumenframework.demo.notes.models;

import java.util.concurrent.atomic.AtomicLong;

import lumen.system.Annotations.LumenEnabled;
import lumen.system.Annotations.Model;

import org.lumenframework.demo.notes.DataStore;
import org.lumenframework.demo.notes.Note;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

@Model
public class TestNoteListModel {
    private static AtomicLong count = new AtomicLong();
    private String key = "test" + count.getAndIncrement() + System.currentTimeMillis();

    public TestNoteListModel() throws Exception {
        createNote("created first", key);
        createNote("created second", key);
        createNote("created third", key);
        createNote("created absolutely last", key);
    }

    @LumenEnabled
    public String getKey() throws Exception {
        return key;
    }

    private void createNote(String title, String body) throws Exception {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        Note note = new Note();
        note.setTitle(title);
        note.setBody(body);
        noteDao.create(note);
    }
}

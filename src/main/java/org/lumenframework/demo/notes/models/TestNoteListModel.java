package org.lumenframework.demo.notes.models;

import java.util.concurrent.atomic.AtomicLong;

import lumen.system.Annotations.LumenEnabled;
import lumen.system.Annotations.Model;

import org.lumenframework.demo.notes.Note;
import org.lumenframework.demo.notes.persist.DataStore;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

@Model
public class TestNoteListModel {
    private static AtomicLong count = new AtomicLong();
    private String key = "test" + count.getAndIncrement() + System.currentTimeMillis();

    public TestNoteListModel() throws Exception {
        createNote("created first", key);
        Thread.sleep(100);
        createNote("created second", key);
        Thread.sleep(100);
        createNote("created third", key);
        Thread.sleep(100);
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

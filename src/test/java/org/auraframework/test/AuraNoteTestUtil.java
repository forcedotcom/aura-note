/*
 * Copyright (C) 2014 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.auraframework.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.auraframework.demo.notes.DataStore;
import org.auraframework.demo.notes.Note;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/**
 * Utility class for Aura Note Webdriver and Unit tests.
 * 
 */
public class AuraNoteTestUtil {
    // CSS selectors for finding elements in WebDriver tests
    public static final String sampleNoteTitle = "Sample Note";
    public static final String sampleNoteBody = "Just a simple note to let you know\nAura\nloves you!";

    public static final String SIDEBAR = "ul.noteList_t > li";

    public static final String NEW_NOTE_BUTTON = ".newNote_t";
    public static final String SAVE_BUTTON = ".save_t";
    public static final String DELETE_BUTTON = ".delete_t";
    public static final String EDIT_BUTTON = ".edit_t";
    public static final String CANCEL_BUTTON = ".cancel_t";
    public static final String SEARCH_BUTTON = ".search_t";
    public static final String LOCATION_BUTTON = ".locationButton";
    public static final String LOCATION_BUTTON1 = "label bBody truncate";
    
    
    public static final String SORT_MENU = ".sort_t";
    public static final String SORT_A_FIRST = ".aFirst_t";
    public static final String SORT_Z_FIRST = ".zFirst_t";
    public static final String SORT_NEWEST_FIRST = ".newestFirst_t";
    public static final String SORT_OLDEST_FIRST = ".oldestFirst_t";

    public static final String SEARCH_BOX = ".searchbox";

    public static final String TITLE_INPUT = ".title_t";
    public static final String BODY_INPUT = ".noteBody > textarea.uiInput";

    public static final String DETAILS_TITLE = "h2.noteTitle";
    public static final String DETAILS_BODY = "p.noteBody";
    public static final String SIDEBAR_TITLE = "div.uiBlock > div.bBody > h2.subject.truncate";
    public static final String SIDEBAR_BODY = "p.desc.truncate";
	

    // Save existing notes to this list while running tests, restore after tests complete
    private static List<Note> notes = new ArrayList<Note>();
    
    /**
     * Saves current notes in database to local list and clears for tests.
     */
    protected static void saveDbState() throws SQLException {
        notes.clear();
        saveNotes();
        clearNotesDb();
    }

    /**
     * Delete notes created by tests and restore original notes.
     */
    protected static void restoreDbState() throws SQLException {
        clearNotesDb();
        restoreSavedNotes();
    }

    protected static void saveNotes() throws SQLException {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        CloseableIterator<Note> iter = noteDao.iterator();
        while (iter.hasNext()) {
            Note curr = iter.next();
            notes.add(curr);
        }
    }

    protected static void restoreSavedNotes() throws SQLException {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        for (Note note : notes) {
            noteDao.create(note);
        }
    }

    protected static void clearNotesDb() throws SQLException {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        CloseableIterator<Note> iter = noteDao.iterator();
        while (iter.hasNext()) {
            Note curr = iter.next();
            noteDao.deleteById(curr.getId());
        }
    }

    /**
     * Searches database for a note with matching title and body strings. If multiple exist, first instance
     * will be returned.
     * @param title Note title.
     * @param body Note body.
     * @return First found instance of Note object with matching title and body.
     */
    public static Note getNoteByTitleBody(String title, String body) throws SQLException {
        // Controller converts null title/body to empty string, mimic behavior
        title = (title == null) ? "" : title;
        body = (body == null) ? "" : body;
        
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        CloseableIterator<Note> iter = noteDao.iterator();
        while (iter.hasNext()) {
            Note curr = iter.next();
            if (curr.getTitle().equals(title) && curr.getBody().equals(body)) {
                return curr;
            }
        }
        return null;
    }

    public static boolean isNoteInDb(String title, String body) throws SQLException {
        return (getNoteByTitleBody(title, body) == null) ? false : true;
    }
}

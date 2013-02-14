/*
 * Copyright (C) 2012 salesforce.com, inc.
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
package org.auraframework.test.basic;

import java.math.BigDecimal;
import java.util.Date;

import org.auraframework.demo.notes.Note;
import org.auraframework.demo.notes.controllers.NoteEditController;
import org.auraframework.test.AuraNoteTestUtil;
import org.auraframework.test.AuraNoteUnitTestCase;

public class NoteEditControllerUnitTest extends AuraNoteUnitTestCase {

    /**
     * Verify able to save a note with a null ID. Setting null as the ID should create a new note in the database.
     */
    public void testNullId() throws Exception {
        String title = titleAsTime();
        String body = bodyAsTime();
        NoteEditController.saveNote(null, title, body, "createdOn.asc", null, null);
        assertTrue("Note not saved to database", AuraNoteTestUtil.isNoteInDb(title, body));
    }

    /**
     * Passing in a non-existent ID should throw a NPE.
     */
    public void testNonexistantId() throws Exception {
        try {
            NoteEditController.saveNote((long) 12344321, "title", "body", "createdOn.asc", null, null);
            fail("Note creation should fail using nonexistant id");
        } catch (NullPointerException expected) {
            // Expected
        }
    }

    /**
     * Passing in null for the note title should default it to an empty string.
     */
    public void testNullTitle() throws Exception {
        String body = bodyAsTime();
        NoteEditController.saveNote(null, null, body, "createdOn.asc", null, null);
        assertTrue("Note not saved to database.", AuraNoteTestUtil.isNoteInDb(null, body));
    }

    /**
     * Passing in null for the note title should default it to an empty string.
     */
    public void testNullBody() throws Exception {
        String title = titleAsTime();
        NoteEditController.saveNote(null, title, null, "createdOn.asc", null, null);
        assertTrue("Note not saved to database.", AuraNoteTestUtil.isNoteInDb(title, null));
    }

    /**
     * Verify GPS coordinates are accuratly attached to a note.
     */
    public void testGpsCoordinates() throws Exception {
        String title = titleAsTime();
        String body = bodyAsTime();
        BigDecimal lat = new BigDecimal(37.826735);
        BigDecimal lon = new BigDecimal(-122.423058);

        NoteEditController.saveNote(null, title, body, "createdOn.asc", lat, lon);
        Note note = AuraNoteTestUtil.getNoteByTitleBody(title, body);
        assertEquals("Latitude of created note is incorrect.", lat.doubleValue(), note.getLatitude());
        assertEquals("Longitude of created note is incorrect.", lon.doubleValue(), note.getLongitude());
    }

    /**
     * Verify Exceptions are thrown for bad sorting types.
     */
    public void testBadSortingMethod() {
        String title = titleAsTime();
        String body = bodyAsTime();
        try {
            NoteEditController.saveNote(null, title, body, "createdOn.foo", null, null);
            fail("Invalid sort type 'createdOn.foo' should not create note");
        } catch (Exception expected) {
            // Expected
        }

        try {
            NoteEditController.saveNote(null, title, body, "foo.asc", null, null);
            fail("Invalid sort type 'foo.asc' should not create note");
        } catch (Exception expected) {
            // Expected
        }

        try {
            NoteEditController.saveNote(null, title, body, "", null, null);
            fail("Empty sort type should not create note");
        } catch (Exception expected) {
            // Expected
        }
    }

    /**
     * Calling saveNote() with a valid ID should edit the note already saved in the database.
     */
    public void testEditNote() throws Exception {
        String title = titleAsTime();
        String body = bodyAsTime();
        NoteEditController.saveNote(null, title, body, "createdOn.asc", null, null);
        Note note = AuraNoteTestUtil.getNoteByTitleBody(title, body);
        NoteEditController.saveNote(note.getId(), "new" + title, "new" + body, "createdOn.asc", null, null);
        assertTrue("Edited note not present in database", AuraNoteTestUtil.isNoteInDb("new" + title, "new" + body));
    }

    private String titleAsTime() {
        return "Title: " + new Date().getTime();
    }

    private String bodyAsTime() {
        return "Body: " + new Date().getTime();
    }
}

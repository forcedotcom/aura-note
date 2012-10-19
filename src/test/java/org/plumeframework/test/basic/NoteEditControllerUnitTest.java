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
package org.plumeframework.test.basic;

import java.math.BigDecimal;
import java.util.Date;

import org.plumeframework.demo.notes.Note;
import org.plumeframework.demo.notes.controllers.NoteEditController;
import org.plumeframework.test.PlumeNoteTestUtil;
import org.plumeframework.test.PlumeNoteUnitTestCase;

public class NoteEditControllerUnitTest extends PlumeNoteUnitTestCase {

    public void testNullId() {
        String title = titleAsTime();
        String body = bodyAsTime();
        try {
            NoteEditController.saveNote(null, title, body, "createdOn.asc", null, null);
            assertTrue("Note not saved to database.", PlumeNoteTestUtil.isNoteInDb(title, body));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Note creation failed with null id.");
        }
    }

    public void testNonexistantId() {
        try {
            NoteEditController.saveNote((long)12344321, "title", "body", "createdOn.asc", null, null);
            fail("Note creation should fail with nonexistant id.");
        } catch (Exception e) {}
    }

    public void testNullTitle() {
        String body = bodyAsTime();
        try {
            NoteEditController.saveNote(null, null, body, "createdOn.asc", null, null);
            assertTrue("Note not saved to database.", PlumeNoteTestUtil.isNoteInDb(null, body));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Note creation failed with null body.");
        }
    }

    public void testNullBody() {
        String title = titleAsTime();
        try {
            NoteEditController.saveNote(null, title, null, "createdOn.asc", null, null);
            assertTrue("Note not saved to database.", PlumeNoteTestUtil.isNoteInDb(title, null));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Note creation failed with null body.");
        }
    }

    public void testGpsCoordinates() {
        String title = titleAsTime();
        String body = bodyAsTime();
        BigDecimal lat = new BigDecimal(37.826735);
        BigDecimal lon = new BigDecimal(-122.423058);

        try {
            NoteEditController.saveNote(null, title, body, "createdOn.asc", lat, lon);
            Note note = PlumeNoteTestUtil.getNoteByTitleBody(title, body);
            assertEquals("Latitude of created note is incorrect.", lat.doubleValue(), note.getLatitude());
            assertEquals("Longitude of created note is incorrect.", lon.doubleValue(), note.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Note creation failed with GPS coordinates.");
        }
    }

    public void testBadSortingMethod() {
        String title = titleAsTime();
        String body = bodyAsTime();
        try {
            NoteEditController.saveNote(null, title, body, "createdOn.foo", null, null);
            fail("Invalid sort type should not create note");
        } catch (Exception e) {}
        
        try {
            NoteEditController.saveNote(null, title, body, "foo.asc", null, null);
            fail("Invalid sort type should not create note");
        } catch (Exception e) {}
        
        try {
            NoteEditController.saveNote(null, title, body, "", null, null);
            fail("Empty sort type should not create note");
        } catch (Exception e) {}
    }

    public void testEditNote() {
        String title = titleAsTime();
        String body = bodyAsTime();
        try {
            NoteEditController.saveNote(null, title, body, "createdOn.asc", null, null);
            Note note = PlumeNoteTestUtil.getNoteByTitleBody(title, body);
            NoteEditController.saveNote(note.getId(), "new"+title, "new"+body, "createdOn.asc", null, null);
            assertTrue("Edited note not present in database.", PlumeNoteTestUtil.isNoteInDb("new"+title, "new"+body));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to edit note already in database.");
        }
    }

    private String titleAsTime() {
        return "Title: " + new Date().getTime();
    }

    private String bodyAsTime() {
        return "Body: " + new Date().getTime();
    }
}

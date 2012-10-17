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

import org.plumeframework.demo.notes.controllers.NoteEditController;
import org.plumeframework.test.PlumeNoteUnitTestCase;

public class NoteEditControllerUnitTest extends PlumeNoteUnitTestCase {

    public void testNullId() {
        try {
            NoteEditController.saveNote(null, "title", "body", "createdOn.asc", null, null);
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
        try {
            NoteEditController.saveNote(null, null, "body", "createdOn.asc", null, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Note creation failed with null body.");
        }
    }

    public void testNullBody() {
        try {
            NoteEditController.saveNote(null, "title", null, "createdOn.asc", null, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Note creation failed with null body.");
        }
    }

    public void testGpsCoordinates() {
        BigDecimal lat = new BigDecimal(37.826735);
        BigDecimal lon = new BigDecimal(-122.423058);
        
        try {
            NoteEditController.saveNote(null, "title", "body", "createdOn.asc", lat, lon);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Note creation failed with GPS coordinates.");
        }
    }
}

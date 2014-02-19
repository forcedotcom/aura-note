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
package org.auraframework.test.basic;

import org.auraframework.demo.notes.Note;
import org.auraframework.test.AuraNoteTestUtil;
import org.auraframework.test.AuraNoteUITestCase;

public class LocationInNoteUITest extends AuraNoteUITestCase {

    public LocationInNoteUITest(String name) {
        super(name);
    }

    // Empty test so we don't get compilation errors
    public void testNothing() {
    }

    // TODO(W-1738235): location tests fail in Autobuilds
    public void _testAddLocation() throws Exception {
        open("/auranote/notes.app");

        Note note = new Note("Hello World", "I am Mr. Roboto");

        // Create first note
        clickElement(AuraNoteTestUtil.NEW_NOTE_BUTTON);
        waitForElementAppear(AuraNoteTestUtil.TITLE_INPUT);
        clickElement(AuraNoteTestUtil.LOCATION_BUTTON);
        clickElement(AuraNoteTestUtil.TITLE_INPUT);
        sendText(note.getTitle(), AuraNoteTestUtil.TITLE_INPUT);
        sendText(note.getBody(), AuraNoteTestUtil.BODY_INPUT);
        String latlng = getText(AuraNoteTestUtil.LOCATION_BUTTON);
        assertFalse("Location is Enabled ", isLocationButtonEnabled());

        clickElement(AuraNoteTestUtil.SAVE_BUTTON);
        waitForSidebarUpdate("First note not added to sidebar", note.getTitle(), note.getBody(), true);

        // Check details view of first Note
        getElementInSidebar(note.getTitle(), note.getBody()).click();
        waitForTextChange("Details view title does not match new note", AuraNoteTestUtil.DETAILS_TITLE, note.getTitle());
        waitForTextChange("Details view body does not match new note", AuraNoteTestUtil.DETAILS_BODY, note.getBody());
        assertEquals("Details view location does not match new note", getText(AuraNoteTestUtil.LOCATION_BUTTON), latlng);
    }
}

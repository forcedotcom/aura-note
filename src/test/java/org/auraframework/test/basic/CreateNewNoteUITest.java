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

import org.auraframework.test.AuraNoteTestUtil;
import org.auraframework.test.AuraNoteUITestCase;

public class CreateNewNoteUITest extends AuraNoteUITestCase {

    public CreateNewNoteUITest(String name) {
        super(name);
    }

    /**
     * Verifies notes are properly created and added to sidebar. Also checks details view of first note after second
     * note is added.
     */
    // TODO(W-1420533): failing in Safari
    public void _testCreateNewNote() throws Exception {
        open("/auranote/notes.app");

        // Create first note
        clickElement(AuraNoteTestUtil.NEW_NOTE_BUTTON);
        waitForElementAppear(AuraNoteTestUtil.TITLE_INPUT);
        clickElement(AuraNoteTestUtil.TITLE_INPUT);
        sendText("New Note 1", AuraNoteTestUtil.TITLE_INPUT);
        sendText("aaa", AuraNoteTestUtil.BODY_INPUT);
        clickElement(AuraNoteTestUtil.SAVE_BUTTON);
        waitForSidebarUpdate("First note not added to sidebar", "New Note 1", "aaa", true);

        clickElement(AuraNoteTestUtil.NEW_NOTE_BUTTON);
        waitForElementAppear(AuraNoteTestUtil.TITLE_INPUT);
        sendText("New Note 2", AuraNoteTestUtil.TITLE_INPUT);
        sendText("bbb", AuraNoteTestUtil.BODY_INPUT);
        clickElement(AuraNoteTestUtil.SAVE_BUTTON);
        waitForSidebarUpdate("Second note not added to sidebar", "New Note 2", "bbb", true);

        // Check details view of first Note
        getElementInSidebar("New Note 1", "aaa").click();
        assertEquals("Details view title does not match new note", "New Note 1", getText(AuraNoteTestUtil.DETAILS_TITLE));
        assertEquals("Details view body does not match new note", "aaa", getText(AuraNoteTestUtil.DETAILS_BODY));
    }

    /**
     * Verifies cancelled note does not get added to sidebar and disappears from details view.
     */
    public void testCancelCreateNewNote() throws Exception {
        open("/auranote/notes.app");

        clickElement(AuraNoteTestUtil.NEW_NOTE_BUTTON);
        waitForElementAppear(AuraNoteTestUtil.TITLE_INPUT);
        sendText("New Note 1", AuraNoteTestUtil.TITLE_INPUT);
        sendText("aaa", AuraNoteTestUtil.BODY_INPUT);
        clickElement(AuraNoteTestUtil.CANCEL_BUTTON);
        waitForElementAppear(AuraNoteTestUtil.DETAILS_TITLE);

        assertFalse("Note added to sidebar after Cancel pressed", isInSidebar("New Note 1", "aaa"));
        assertEquals("Note title still visible in details page", "", getText(AuraNoteTestUtil.DETAILS_TITLE));
        assertEquals("Note body still visible in details page", "", getText(AuraNoteTestUtil.DETAILS_BODY));
    }

    /**
     * Verifies note with empty title and body can be created.
     */
    public void testCreateEmptyNote() throws Exception {
        open("/auranote/notes.app");

        clickElement(AuraNoteTestUtil.NEW_NOTE_BUTTON);
        waitForElementAppear(AuraNoteTestUtil.TITLE_INPUT);
        sendText("", AuraNoteTestUtil.TITLE_INPUT);
        sendText("", AuraNoteTestUtil.BODY_INPUT);
        clickElement(AuraNoteTestUtil.SAVE_BUTTON);
        waitForSidebarUpdate("First note not added to sidebar", "", "", true);

        assertEquals("Details view title does not match new note", "", getText(AuraNoteTestUtil.DETAILS_TITLE));
        assertEquals("Details view body does not match new note", "", getText(AuraNoteTestUtil.DETAILS_BODY));
    }
}

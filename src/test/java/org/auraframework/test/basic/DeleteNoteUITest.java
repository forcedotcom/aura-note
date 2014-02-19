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

import java.util.HashMap;

import org.auraframework.test.AuraNoteTestUtil;
import org.auraframework.test.AuraNoteUITestCase;

public class DeleteNoteUITest extends AuraNoteUITestCase {

    public DeleteNoteUITest(String name) {
        super(name);
    }

    public void testDeleteSingleNote() throws Exception {
        open("/auranote/notes.app");

        HashMap<String, String> newNotes = new HashMap<String, String>();
        newNotes.put("New Note 1", "aaa");
        newNotes.put("New Note 2", "bbb");
        newNotes.put("New Note 3", "ccc");
        createNewNotes(newNotes);

        getElementInSidebar("New Note 1", "aaa").click();
        waitForElementAppear(AuraNoteTestUtil.DELETE_BUTTON);
        clickElement(AuraNoteTestUtil.DELETE_BUTTON);
        waitForSidebarUpdate("Note never deleted from sidebar", "New Note 1", "aaa", false);

        assertFalse("All notes deleted from sidebar", getSidebar().isEmpty());
        assertTrue("Wrong note deleted from sidebar", isInSidebar("New Note 2", "bbb"));
        assertTrue("Wrong note deleted from sidebar", isInSidebar("New Note 3", "ccc"));

        // Check details view, should be New Note screen
        assertEquals("Note title still displayed in details view", "", getText(AuraNoteTestUtil.TITLE_INPUT));
        assertEquals("Note body still displayed in details view", "", getText(AuraNoteTestUtil.BODY_INPUT));
    }

    public void testDeleteAllNotes() throws Exception {
        open("/auranote/notes.app");

        HashMap<String, String> newNotes = new HashMap<String, String>();
        newNotes.put("New Note 1", "aaa");
        newNotes.put("New Note 2", "bbb");
        newNotes.put("New Note 3", "ccc");
        createNewNotes(newNotes);

        // Delete notes from sidebar one at a time
        for (int i = 0; i < newNotes.size(); i++) {
            getSidebar().get(0).click();
            waitForElementAppear(AuraNoteTestUtil.DELETE_BUTTON);
            clickElement(AuraNoteTestUtil.DELETE_BUTTON);
            waitForElementAppear(AuraNoteTestUtil.SAVE_BUTTON);
        }

        // At this point only the Sample Note should be in sidebar
        assertFalse("Deleted note still in sidebar", isInSidebar("New Note 1", "aaa"));
        assertFalse("Deleted note still in sidebar", isInSidebar("New Note 2", "bbb"));
        assertFalse("Deleted note still in sidebar", isInSidebar("New Note 3", "ccc"));
        assertEquals("Not all notes deleted from sidebar", 1, getSidebar().size());
        assertTrue("Sample Note not found in sidebar",
                isInSidebar(AuraNoteTestUtil.sampleNoteTitle, AuraNoteTestUtil.sampleNoteBody));

        assertEquals("Note title still displayed in details view", "", getText(AuraNoteTestUtil.TITLE_INPUT));
        assertEquals("Note body still displayed in details view", "", getText(AuraNoteTestUtil.BODY_INPUT));
    }

    /**
     * Create a note and then delete it without changing focus away from newly created note.
     */
    public void testCreateThenDeleteNote() throws Exception {
        open("/auranote/notes.app");

        clickElement(AuraNoteTestUtil.NEW_NOTE_BUTTON);
        waitForElementAppear(AuraNoteTestUtil.TITLE_INPUT);
        sendText("Create Delete", AuraNoteTestUtil.TITLE_INPUT);
        sendText("aaa", AuraNoteTestUtil.BODY_INPUT);
        clickElement(AuraNoteTestUtil.SAVE_BUTTON);
        waitForElementAppear(AuraNoteTestUtil.DELETE_BUTTON);
        clickElement(AuraNoteTestUtil.DELETE_BUTTON);
        waitForElementAppear(AuraNoteTestUtil.TITLE_INPUT);

        assertEquals("Deleted Note title still displayed in details view", "", getText(AuraNoteTestUtil.TITLE_INPUT));
        assertEquals("Deleted Note body still displayed in details view", "", getText(AuraNoteTestUtil.BODY_INPUT));
        assertFalse("Deleted Note still in sidebar", isInSidebar("Create Delete", "aaa"));
    }
}

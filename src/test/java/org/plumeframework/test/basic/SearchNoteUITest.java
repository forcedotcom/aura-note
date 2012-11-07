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

import java.util.HashMap;

import org.plumeframework.test.PlumeNoteTestUtil;
import org.plumeframework.test.PlumeNoteUITestCase;

public class SearchNoteUITest extends PlumeNoteUITestCase {

    public SearchNoteUITest(String name) {
        super(name);
    }

    /**
     * Verifies search function finds notes with matching title.
     */
    public void testSearchNoteTitle() throws Exception {
        open("/plumenote/notes.app");
        addInitialNotes();

        sendText("Elephant", PlumeNoteTestUtil.SEARCH_BOX);
        clickElement(PlumeNoteTestUtil.SEARCH_BUTTON);
        waitForSidebarUpdate("Not all expected notes removed after search", "Banana", "bbb", false);

        assertEquals("Not all expected notes removed after search", 1, getSidebar().size());
        assertTrue("Search does not display note in sidebar", isInSidebar("Elephant", "eee banana"));

        getSidebar().get(0).click();
        assertEquals("Details view title does not match Note searched for", "Elephant", getText(PlumeNoteTestUtil.DETAILS_TITLE));
        assertEquals("Details view body does not match Note searched for", "eee banana", getText(PlumeNoteTestUtil.DETAILS_BODY));
    }

    /**
     * Verifies search function finds notes with matching body.
     */
    public void testSearchNoteBody() throws Exception {
        open("/plumenote/notes.app");
        addInitialNotes();

        sendText("eee", PlumeNoteTestUtil.SEARCH_BOX);
        clickElement(PlumeNoteTestUtil.SEARCH_BUTTON);
        waitForSidebarUpdate("Not all expected notes removed after search", "Banana", "bbb", false);

        assertEquals("Not all expected notes removed after search", 1, getSidebar().size());
        assertTrue("Note searched for not in sidebar", isInSidebar("Elephant", "eee banana"));

        getSidebar().get(0).click();
        assertEquals("Details view title does not match Note searched for", "Elephant", getText(PlumeNoteTestUtil.DETAILS_TITLE));
        assertEquals("Details view body does not match Note searched for", "eee banana", getText(PlumeNoteTestUtil.DETAILS_BODY));
    }

    public void testSearchNoMatch() throws Exception {
        open("/plumenote/notes.app");
        addInitialNotes();

        sendText("nonexistent search zzzzz", PlumeNoteTestUtil.SEARCH_BOX);
        clickElement(PlumeNoteTestUtil.SEARCH_BUTTON);
        waitForSidebarUpdate("Not all expected notes removed after search", "Banana", "bbb", false);

        // Only sample note should be present
        assertEquals("Not all expected notes removed after search", 1, getSidebar().size());
        getSidebar().get(0).click();
        assertEquals("Details view title does not match Note searched for", PlumeNoteTestUtil.sampleNoteTitle, 
                        getText(PlumeNoteTestUtil.DETAILS_TITLE));
        assertEquals("Details view body does not match Note searched for", PlumeNoteTestUtil.sampleNoteBody, 
                        getText(PlumeNoteTestUtil.DETAILS_BODY));
    }

    /**
     * Verifies search function finds multiple notes. One note has matching title and the other has matching body.
     */
    public void testSearchTitleAndBodySeparateNotesMatch() throws Exception {
        open("/plumenote/notes.app");
        addInitialNotes();

        sendText("banana", PlumeNoteTestUtil.SEARCH_BOX);
        clickElement(PlumeNoteTestUtil.SEARCH_BUTTON);
        waitForSidebarUpdate("Not all expected notes removed after search", "Dog", "ddd", false);

        // Only sample note should be present
        assertEquals("Not all expected notes removed after search", 2, getSidebar().size());
        assertTrue("Not all expected search results shown in sidebar", isInSidebar("Banana", "bbb"));
        assertTrue("Not all expected search results shown in sidebar", isInSidebar("Elephant", "eee banana"));
    }

    /**
     * Verifies searching for a recently deleted note does not show any results.
     */
    public void testSearchDeletedNote() throws Exception {
        open("/plumenote/notes.app");
        addInitialNotes();

        getElementInSidebar("Banana", "bbb").click();
        waitForElementAppear(PlumeNoteTestUtil.DELETE_BUTTON);
        clickElement(PlumeNoteTestUtil.DELETE_BUTTON);
        waitForSidebarUpdate("Note never deleted from sidebar", "Banana", "bbb", false);

        sendText("bbb", PlumeNoteTestUtil.SEARCH_BOX);
        clickElement(PlumeNoteTestUtil.SEARCH_BUTTON);
        waitForSidebarUpdate("Not all expected notes removed after search", "Dog", "ddd", false);

        assertFalse("Deleted Note still found in search", isInSidebar("Banana", "bbb"));
    }

    /**
     * Verifies searching for a recently edited note does not show any results.
     */
    // W-1420533
    public void _testSearchEditedNote() throws Exception {
        open("/plumenote/notes.app");
        addInitialNotes();

        getElementInSidebar("Banana", "bbb").click();
        waitForElementAppear(PlumeNoteTestUtil.EDIT_BUTTON);
        clickElement(PlumeNoteTestUtil.EDIT_BUTTON);
        waitForElementAppear(PlumeNoteTestUtil.TITLE_INPUT);
        sendText("Zebra", PlumeNoteTestUtil.TITLE_INPUT);
        sendText("zzz", PlumeNoteTestUtil.BODY_INPUT);
        clickElement(PlumeNoteTestUtil.SAVE_BUTTON);
        waitForSidebarUpdate("Edited note not found in sidebar", "Zebra", "zzz", true);

        sendText("bbb", PlumeNoteTestUtil.SEARCH_BOX);
        clickElement(PlumeNoteTestUtil.SEARCH_BUTTON);
        waitForSidebarUpdate("Not all expected notes removed after search", "Dog", "ddd", false);

        assertFalse("Edited Note still found in search", isInSidebar("Banana", "bbb"));
    }

    private void addInitialNotes() throws Exception {
        HashMap<String, String> newNotes = new HashMap<String, String>();
        newNotes.put("Cat Dog", "ccc");
        newNotes.put("Dog", "ddd");
        newNotes.put("Apple Dog", "aaa");
        newNotes.put("Banana", "bbb");
        newNotes.put("Elephant", "eee banana");
        createNewNotes(newNotes);
    }
}
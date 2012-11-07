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

import org.plumeframework.test.PlumeNoteTestUtil;
import org.plumeframework.test.PlumeNoteUITestCase;

public class EditNoteUITest extends PlumeNoteUITestCase {
    private String editedTitle = "Edited Note 1";
    private String editedBody = "bbb";

    public EditNoteUITest(String name) {
        super(name);
    }

    /**
     * All tests are currently disabled. This filler function prevents failures while running integration tests.
     * Remove this function when at least one test is running.
     */
    public void testFiller() {}

    // W-1420533 - failing in Safari
    public void _testEditNote() throws Exception {
        open("/plumenote/notes.app");

        createNewNote("New Note 1", "aaa");

        getSidebar().get(0).click();
        waitForElementAppear(PlumeNoteTestUtil.EDIT_BUTTON);

        clickElement(PlumeNoteTestUtil.EDIT_BUTTON);
        waitForElementAppear(PlumeNoteTestUtil.TITLE_INPUT);

        sendText(editedTitle, PlumeNoteTestUtil.TITLE_INPUT);
        sendText(editedBody, PlumeNoteTestUtil.BODY_INPUT);
        clickElement(PlumeNoteTestUtil.SAVE_BUTTON);
        waitForSidebarUpdate("Edited note not found in sidebar", editedTitle, editedBody, true);

        assertEquals("Edited note title not displayed in details view", editedTitle, getText(PlumeNoteTestUtil.DETAILS_TITLE));    
        assertEquals("Edited note body not displayed in details view", editedBody, getText(PlumeNoteTestUtil.DETAILS_BODY));

        getElementInSidebar(editedTitle, editedBody).click();
        waitForElementAppear(PlumeNoteTestUtil.DETAILS_TITLE);
        assertEquals("Edited note title not displayed in details view after selecting from sidebar", editedTitle, 
                        getText(PlumeNoteTestUtil.DETAILS_TITLE));    
        assertEquals("Edited note body not displayed in details view after selecting from sidebar", editedBody, 
                        getText(PlumeNoteTestUtil.DETAILS_BODY));
    }

    /**
     * Verify pressing cancel on edited note does not modify note contents.
     */
    // W-1381022
    public void _testCancelEditNote() throws Exception {
        open("/plumenote/notes.app");

        createNewNote("New Note 1", "aaa");

        getSidebar().get(0).click();        
        waitForElementAppear(PlumeNoteTestUtil.EDIT_BUTTON);

        clickElement(PlumeNoteTestUtil.EDIT_BUTTON);
        waitForElementAppear(PlumeNoteTestUtil.TITLE_INPUT);

        sendText(editedTitle, PlumeNoteTestUtil.TITLE_INPUT);
        sendText(editedBody, PlumeNoteTestUtil.BODY_INPUT);
        clickElement(PlumeNoteTestUtil.CANCEL_BUTTON);
        waitForElementAppear(PlumeNoteTestUtil.DETAILS_TITLE);

        assertTrue("Original Note not found in sidebar", isInSidebar("New Note 1", "aaa"));
        assertEquals("Originial Note title not displayed in details view", "New Note 1", getText(PlumeNoteTestUtil.DETAILS_TITLE));    
        assertEquals("Originial Note body not displayed in details view", "aaa", getText(PlumeNoteTestUtil.DETAILS_BODY));

        getElementInSidebar("New Note 1", "aaa").click();
        waitForElementAppear(PlumeNoteTestUtil.DETAILS_TITLE);
        assertEquals("Original Note title not displayed in details view after selecting from sidebar", editedTitle, 
                        getText(PlumeNoteTestUtil.DETAILS_TITLE));    
        assertEquals("Original Note body not displayed in details view after selecting from sidebar", editedBody, 
                        getText(PlumeNoteTestUtil.DETAILS_BODY));
    }

}

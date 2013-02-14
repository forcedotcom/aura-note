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

public class EditNoteUITest extends AuraNoteUITestCase {
    private final String editedTitle = "Edited Note 1";
    private final String editedBody = "bbb";

    public EditNoteUITest(String name) {
        super(name);
    }

    /**
     * Verify basic editing functionality.
     */
    public void testEditNote() throws Exception {
        open("/auranote/notes.app");

        createNewNote("New Note 1", "aaa");

        getSidebar().get(0).click();
        waitForElementAppear(AuraNoteTestUtil.EDIT_BUTTON);

        clickElement(AuraNoteTestUtil.EDIT_BUTTON);
        waitForElementAppear(AuraNoteTestUtil.TITLE_INPUT);

        sendText(editedTitle, AuraNoteTestUtil.TITLE_INPUT);
        sendText(editedBody, AuraNoteTestUtil.BODY_INPUT);
        clickElement(AuraNoteTestUtil.SAVE_BUTTON);
        waitForSidebarUpdate("Edited note not found in sidebar", editedTitle, editedBody, true);

        assertEquals("Edited note title not displayed in details view", editedTitle,
                getText(AuraNoteTestUtil.DETAILS_TITLE));
        assertEquals("Edited note body not displayed in details view", editedBody,
                getText(AuraNoteTestUtil.DETAILS_BODY));

        getElementInSidebar(editedTitle, editedBody).click();
        waitForElementAppear(AuraNoteTestUtil.DETAILS_TITLE);
        assertEquals("Edited note title not displayed in details view after selecting from sidebar", editedTitle,
                getText(AuraNoteTestUtil.DETAILS_TITLE));
        assertEquals("Edited note body not displayed in details view after selecting from sidebar", editedBody,
                getText(AuraNoteTestUtil.DETAILS_BODY));
    }

    /**
     * Verify pressing cancel on edited note does not modify note contents.
     */
    // TODO(W-1381022): canceling edit will still display modified text
    public void _testCancelEditNote() throws Exception {
        open("/auranote/notes.app");

        createNewNote("New Note 1", "aaa");

        getSidebar().get(0).click();
        waitForElementAppear(AuraNoteTestUtil.EDIT_BUTTON);

        clickElement(AuraNoteTestUtil.EDIT_BUTTON);
        waitForElementAppear(AuraNoteTestUtil.TITLE_INPUT);

        sendText(editedTitle, AuraNoteTestUtil.TITLE_INPUT);
        sendText(editedBody, AuraNoteTestUtil.BODY_INPUT);
        clickElement(AuraNoteTestUtil.CANCEL_BUTTON);
        waitForElementAppear(AuraNoteTestUtil.DETAILS_TITLE);

        assertTrue("Original Note not found in sidebar", isInSidebar("New Note 1", "aaa"));
        assertEquals("Originial Note title not displayed in details view", "New Note 1",
                getText(AuraNoteTestUtil.DETAILS_TITLE));
        assertEquals("Originial Note body not displayed in details view", "aaa", getText(AuraNoteTestUtil.DETAILS_BODY));

        getElementInSidebar("New Note 1", "aaa").click();
        waitForElementAppear(AuraNoteTestUtil.DETAILS_TITLE);
        assertEquals("Original Note title not displayed in details view after selecting from sidebar", editedTitle,
                getText(AuraNoteTestUtil.DETAILS_TITLE));
        assertEquals("Original Note body not displayed in details view after selecting from sidebar", editedBody,
                getText(AuraNoteTestUtil.DETAILS_BODY));
    }

}

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

import org.junit.Ignore;
import org.plumeframework.test.NoteTestUtil;
import org.plumeframework.test.PlumeNoteTestCase;

public class EditNoteTest extends PlumeNoteTestCase {
	private String editedTitle = "Edited Note 1";
	private String editedBody = "bbb";

	public EditNoteTest(String name) {
		super(name);
	}

	public void testEditNote() throws Exception {
		open("/plumenote/notes.app");

	    createNewNote("New Note 1", "aaa");
	    	
	    getSidebar().get(0).click();    	
	    waitForElementAppear(NoteTestUtil.EDIT_BUTTON);
	    	
	    clickElement(NoteTestUtil.EDIT_BUTTON);
	    waitForElementAppear(NoteTestUtil.TITLE_INPUT);
	    	
	    sendText(editedTitle, NoteTestUtil.TITLE_INPUT);
	    sendText(editedBody, NoteTestUtil.BODY_INPUT);
	    clickElement(NoteTestUtil.SAVE_BUTTON);
	    waitForSidebarUpdate("Edited note not found in sidebar", editedTitle, editedBody, true);
	    	
	    assertEquals("Edited note title not displayed in details view", editedTitle, getText(NoteTestUtil.DETAILS_TITLE));	
	    assertEquals("Edited note body not displayed in details view", editedBody, getText(NoteTestUtil.DETAILS_BODY));
	    
	    getElementInSidebar(editedTitle, editedBody).click();
	    waitForElementAppear(NoteTestUtil.DETAILS_TITLE);
	    assertEquals("Edited note title not displayed in details view after selecting from sidebar", editedTitle, getText(NoteTestUtil.DETAILS_TITLE));	
	    assertEquals("Edited note body not displayed in details view after selecting from sidebar", editedBody, getText(NoteTestUtil.DETAILS_BODY));
	}
	
	@Ignore("W-1381022")
	public void _testCancelEditNote() throws Exception {
		open("/plumenote/notes.app");

	    createNewNote("New Note 1", "aaa");
	    
	    getSidebar().get(0).click();    	
	    waitForElementAppear(NoteTestUtil.EDIT_BUTTON);
	    
	    clickElement(NoteTestUtil.EDIT_BUTTON);
	    waitForElementAppear(NoteTestUtil.TITLE_INPUT);
    	
	    sendText(editedTitle, NoteTestUtil.TITLE_INPUT);
	    sendText(editedBody, NoteTestUtil.BODY_INPUT);
	    clickElement(NoteTestUtil.CANCEL_BUTTON);
	    waitForElementAppear(NoteTestUtil.DETAILS_TITLE);
	    
	    assertTrue("Original Note not found in sidebar", isInSidebar("New Note 1", "aaa"));
	    assertEquals("Originial Note title not displayed in details view", "New Note 1", getText(NoteTestUtil.DETAILS_TITLE));	
	    assertEquals("Originial Note body not displayed in details view", "aaa", getText(NoteTestUtil.DETAILS_BODY));
	    
	    getElementInSidebar("New Note 1", "aaa").click();
	    waitForElementAppear(NoteTestUtil.DETAILS_TITLE);
	    assertEquals("Original Note title not displayed in details view after selecting from sidebar", editedTitle, getText(NoteTestUtil.DETAILS_TITLE));	
	    assertEquals("Original Note body not displayed in details view after selecting from sidebar", editedBody, getText(NoteTestUtil.DETAILS_BODY));
	}
	
}

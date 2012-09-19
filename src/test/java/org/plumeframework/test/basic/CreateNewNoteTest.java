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

import org.plumeframework.test.NoteTestUtil;
import org.plumeframework.test.PlumeNoteTestCase;

public class CreateNewNoteTest extends PlumeNoteTestCase {
	
	public CreateNewNoteTest(String name) {
		super(name);
	}
	
    public void testCreateNewNote() throws Exception {
    	open("/plumenote/notes.app");
    		
	    // Create first note
		clickElement(NoteTestUtil.NEW_NOTE_BUTTON);
		waitForElementAppear(NoteTestUtil.TITLE_INPUT);
		sendText("New Note 1", NoteTestUtil.TITLE_INPUT);
		sendText("aaa", NoteTestUtil.BODY_INPUT);
		clickElement(NoteTestUtil.SAVE_BUTTON);		
		waitForSidebarUpdate("First note not added to sidebar", "New Note 1", "aaa", true);
			
		clickElement(NoteTestUtil.NEW_NOTE_BUTTON);
		waitForElementAppear(NoteTestUtil.TITLE_INPUT);
		sendText("New Note 2", NoteTestUtil.TITLE_INPUT);
		sendText("bbb", NoteTestUtil.BODY_INPUT);
		clickElement(NoteTestUtil.SAVE_BUTTON);
		waitForSidebarUpdate("Second note not added to sidebar", "New Note 2", "bbb", true);
			
	    // Check details view of first Note
	    getElementInSidebar("New Note 1", "aaa").click();
	    assertEquals("Details view title does not match new note", "New Note 1", getText(NoteTestUtil.DETAILS_TITLE));
	    assertEquals("Details view body does not match new note", "aaa", getText(NoteTestUtil.DETAILS_BODY));
    }
    
    public void testCancelCreateNewNote() throws Exception {
    	open("/plumenote/notes.app");
		
		clickElement(NoteTestUtil.NEW_NOTE_BUTTON);
		waitForElementAppear(NoteTestUtil.TITLE_INPUT);
		sendText("New Note 1", NoteTestUtil.TITLE_INPUT);
		sendText("aaa", NoteTestUtil.BODY_INPUT);
		clickElement(NoteTestUtil.CANCEL_BUTTON);
		waitForElementAppear(NoteTestUtil.DETAILS_TITLE);  
		
		assertFalse("Note added to sidebar after Cancel pressed", isInSidebar("New Note 1", "aaa"));
		// Right now details view appears with blank title/body. Might make more sense to have
		// note edit (New Note) screen appear
		assertEquals("Note title still visible in details page", "", getText(NoteTestUtil.DETAILS_TITLE));
		assertEquals("Note body still visible in details page", "", getText(NoteTestUtil.DETAILS_BODY));
    }
    
    public void testCreateEmptyNote() throws Exception {
    	open("/plumenote/notes.app");
    	
    	clickElement(NoteTestUtil.NEW_NOTE_BUTTON);
    	waitForElementAppear(NoteTestUtil.TITLE_INPUT);
		sendText("", NoteTestUtil.TITLE_INPUT);
		sendText("", NoteTestUtil.BODY_INPUT);
		clickElement(NoteTestUtil.SAVE_BUTTON);		
		waitForSidebarUpdate("First note not added to sidebar", "", "", true);
		
		assertEquals("Details view title does not match new note", "", getText(NoteTestUtil.DETAILS_TITLE));
	    assertEquals("Details view body does not match new note", "", getText(NoteTestUtil.DETAILS_BODY));
    }
}

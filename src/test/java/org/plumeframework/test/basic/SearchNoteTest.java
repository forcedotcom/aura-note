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

import org.plumeframework.test.NoteTestUtil;
import org.plumeframework.test.PlumeNoteTestCase;



public class SearchNoteTest extends PlumeNoteTestCase {
	
	public SearchNoteTest(String name) {
		super(name);
	}

	public void testSearchNoteTitle() throws Exception {
		open("/plumenote/notes.app");
		addInitialNotes();
		
		sendText("Elephant", NoteTestUtil.SEARCH_BOX);
		clickElement(NoteTestUtil.SEARCH_BUTTON);
		waitForSidebarUpdate("Not all expected notes removed after search", "Banana", "bbb", false);
		
		assertEquals("Not all expected notes removed after search", 1, getSidebar().size());
		assertTrue("Note searched for not in sidebar", isInSidebar("Elephant", "eee banana"));
		
		getSidebar().get(0).click();
		assertEquals("Details view title does not match Note searched for", "Elephant", getText(NoteTestUtil.DETAILS_TITLE));
	    assertEquals("Details view body does not match Note searched for", "eee banana", getText(NoteTestUtil.DETAILS_BODY));
	}
	
	public void testSearchNoteBody() throws Exception {
		open("/plumenote/notes.app");
		addInitialNotes();
		
		sendText("eee", NoteTestUtil.SEARCH_BOX);
		clickElement(NoteTestUtil.SEARCH_BUTTON);
		waitForSidebarUpdate("Not all expected notes removed after search", "Banana", "bbb", false);
		
		assertEquals("Not all expected notes removed after search", 1, getSidebar().size());
		assertTrue("Note searched for not in sidebar", isInSidebar("Elephant", "eee banana"));
		
		getSidebar().get(0).click();
		assertEquals("Details view title does not match Note searched for", "Elephant", getText(NoteTestUtil.DETAILS_TITLE));
	    assertEquals("Details view body does not match Note searched for", "eee banana", getText(NoteTestUtil.DETAILS_BODY));
	}
	
	public void testSearchNoMatch() throws Exception {
		open("/plumenote/notes.app");
		addInitialNotes();
		
		sendText("nonexistent search zzzzz", NoteTestUtil.SEARCH_BOX);
		clickElement(NoteTestUtil.SEARCH_BUTTON);
		waitForSidebarUpdate("Not all expected notes removed after search", "Banana", "bbb", false);
		
		// Only sample note should be present
		assertEquals("Not all expected notes removed after search", 1, getSidebar().size());
		getSidebar().get(0).click();
		assertEquals("Details view title does not match Note searched for", NoteTestUtil.sampleNoteTitle, getText(NoteTestUtil.DETAILS_TITLE));
	    assertEquals("Details view body does not match Note searched for", NoteTestUtil.sampleNoteBody, getText(NoteTestUtil.DETAILS_BODY));
	}
	
	public void testSearchTitleAndBodySeparateNotesMatch() throws Exception {
		open("/plumenote/notes.app");
		addInitialNotes();
		
		sendText("banana", NoteTestUtil.SEARCH_BOX);
		clickElement(NoteTestUtil.SEARCH_BUTTON);
		waitForSidebarUpdate("Not all expected notes removed after search", "Dog", "ddd", false);
		
		// Only sample note should be present
		assertEquals("Not all expected notes removed after search", 2, getSidebar().size());
		assertTrue("Not all expected search results shown in sidebar", isInSidebar("Banana", "bbb"));
		assertTrue("Not all expected search results shown in sidebar", isInSidebar("Elephant", "eee banana"));
	}
	
	public void testSearchDeletedNote() throws Exception {
		open("/plumenote/notes.app");
		addInitialNotes();
		
		getElementInSidebar("Banana", "bbb").click();
    	waitForElementAppear(NoteTestUtil.DELETE_BUTTON);
    	clickElement(NoteTestUtil.DELETE_BUTTON);
    	waitForSidebarUpdate("Note never deleted from sidebar", "Banana", "bbb", false);
		
		sendText("bbb", NoteTestUtil.SEARCH_BOX);
		clickElement(NoteTestUtil.SEARCH_BUTTON);
		waitForSidebarUpdate("Not all expected notes removed after search", "Dog", "ddd", false);
		
		assertFalse("Deleted Note still found in search", isInSidebar("Banana", "bbb"));
	}
	
	public void testSearchEditedNote() throws Exception {
		open("/plumenote/notes.app");
		addInitialNotes();
		
		getElementInSidebar("Banana", "bbb").click();
    	waitForElementAppear(NoteTestUtil.EDIT_BUTTON);
    	clickElement(NoteTestUtil.EDIT_BUTTON);
    	waitForElementAppear(NoteTestUtil.TITLE_INPUT);
	    sendText("Zebra", NoteTestUtil.TITLE_INPUT);
	    sendText("zzz", NoteTestUtil.BODY_INPUT);
	    clickElement(NoteTestUtil.SAVE_BUTTON);
	    waitForSidebarUpdate("Edited note not found in sidebar", "Zebra", "zzz", true);
	    
		sendText("bbb", NoteTestUtil.SEARCH_BOX);
		clickElement(NoteTestUtil.SEARCH_BUTTON);
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

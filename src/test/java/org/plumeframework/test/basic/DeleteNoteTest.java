package org.plumeframework.test.basic;

import java.util.HashMap;

import org.plumeframework.test.NoteTestUtil;
import org.plumeframework.test.PlumeNoteTestCase;



public class DeleteNoteTest extends PlumeNoteTestCase {

	public DeleteNoteTest(String name) {
		super(name);
	}
	
	public void testDeleteSingleNote() throws Exception {
		open("/plumenote/notes.app");
    	
    	HashMap<String, String> newNotes = new HashMap<String, String>();
    	newNotes.put("New Note 1", "aaa");
    	newNotes.put("New Note 2", "bbb");
    	newNotes.put("New Note 3", "ccc");
    	createNewNotes(newNotes);
	    	
    	getElementInSidebar("New Note 1", "aaa").click();
    	waitForElementAppear(NoteTestUtil.DELETE_BUTTON);
    	clickElement(NoteTestUtil.DELETE_BUTTON);
    	waitForSidebarUpdate("Note never deleted from sidebar", "New Note 1", "aaa", false);
    		
    	// Make sure sidebar properly updated
    	assertFalse("All notes deleted from sidebar", getSidebar().isEmpty());
    	assertTrue("Wrong note deleted from sidebar", isInSidebar("New Note 2", "bbb"));
    	assertTrue("Wrong note deleted from sidebar", isInSidebar("New Note 3", "ccc"));
	    	
    	// Check details view, should be New Note screen
    	assertEquals("Note title still displayed in details view", "", getText(NoteTestUtil.TITLE_INPUT));	
    	assertEquals("Note body still displayed in details view", "", getText(NoteTestUtil.BODY_INPUT));	
	}
	
	public void testDeleteAllNotes() throws Exception {
		open("/plumenote/notes.app");
	    	
		HashMap<String, String> newNotes = new HashMap<String, String>();
    	newNotes.put("New Note 1", "aaa");
    	newNotes.put("New Note 2", "bbb");
    	newNotes.put("New Note 3", "ccc");
    	createNewNotes(newNotes);
	    	
    	// Delete each note from sidebar
    	for (int i = 0; i < newNotes.size(); i++) {
    		getSidebar().get(0).click();
    		waitForElementAppear(NoteTestUtil.DELETE_BUTTON);
    		clickElement(NoteTestUtil.DELETE_BUTTON);
    		waitForElementAppear(NoteTestUtil.SAVE_BUTTON);	    		
    	}
	    	
    	// At this point only the Sample Note should be in sidebar
    	assertFalse("Deleted note still in sidebar", isInSidebar("New Note 1", "aaa"));
    	assertFalse("Deleted note still in sidebar", isInSidebar("New Note 2", "bbb"));
    	assertFalse("Deleted note still in sidebar", isInSidebar("New Note 3", "ccc"));
    	assertEquals("Not all notes deleted from sidebar", 1, getSidebar().size());
    	assertTrue("Sample Note not found in sidebar", isInSidebar(NoteTestUtil.sampleNoteTitle, NoteTestUtil.sampleNoteBody));
			
   		// Check details view
    	assertEquals("Note title still displayed in details view", "", getText(NoteTestUtil.TITLE_INPUT));	
    	assertEquals("Note body still displayed in details view", "", getText(NoteTestUtil.BODY_INPUT));
	}
	
	public void testCreateThenDeleteNote() throws Exception {
		open("/plumenote/notes.app");
		
		clickElement(NoteTestUtil.NEW_NOTE_BUTTON);
		waitForElementAppear(NoteTestUtil.TITLE_INPUT);
		sendText("Create Delete", NoteTestUtil.TITLE_INPUT);
		sendText("aaa", NoteTestUtil.BODY_INPUT);
		clickElement(NoteTestUtil.SAVE_BUTTON);		
		waitForElementAppear(NoteTestUtil.DELETE_BUTTON);		
		clickElement(NoteTestUtil.DELETE_BUTTON);
		waitForElementAppear(NoteTestUtil.TITLE_INPUT);
		
		assertEquals("Deleted Note title still displayed in details view", "", getText(NoteTestUtil.TITLE_INPUT));	
    	assertEquals("Deleted Note body still displayed in details view", "", getText(NoteTestUtil.BODY_INPUT)); 	
    	assertFalse("Deleted Note still in sidebar", isInSidebar("Create Delete", "aaa"));
	}
}

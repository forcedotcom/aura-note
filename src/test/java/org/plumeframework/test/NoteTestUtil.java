package org.plumeframework.test;

/**
 * CSS Selectors for finding elements.
 * 
 */
public class NoteTestUtil {
	// Sample Note is displayed when note list is empty
	public static final String sampleNoteTitle = "Sample Note";
	public static final String sampleNoteBody = "Just a simple note to let you know\nPlume\nloves you!";

	public static final String SIDEBAR = "ul.noteList_t > li";
	
	public static final String NEW_NOTE_BUTTON = ".newNote_t";
	public static final String SAVE_BUTTON = ".save_t";
	public static final String DELETE_BUTTON = ".delete_t";
	public static final String EDIT_BUTTON = ".edit_t";
	public static final String CANCEL_BUTTON = ".cancel_t";
	public static final String SEARCH_BUTTON = ".search_t";
	
	public static final String SORT_MENU = ".sort_t";
	public static final String SORT_A_FIRST = ".aFirst_t";
	public static final String SORT_Z_FIRST = ".zFirst_t";
	public static final String SORT_NEWEST_FIRST = ".newestFirst_t";
	public static final String SORT_OLDEST_FIRST = ".oldestFirst_t";
	
	public static final String SEARCH_BOX = ".searchbox";
	
	public static final String TITLE_INPUT = ".title_t";
	public static final String BODY_INPUT = ".noteBody > textarea.uiInput";
	
	public static final String DETAILS_TITLE = "h2.noteTitle";
	public static final String DETAILS_BODY = "p.noteBody";
	public static final String SIDEBAR_TITLE = "div.uiBlock > div.bBody > h2.subject.truncate";
	public static final String SIDEBAR_BODY = "p.desc.truncate";
}

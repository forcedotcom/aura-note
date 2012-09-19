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
import java.util.List;

import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.plumeframework.test.NoteTestUtil;
import org.plumeframework.test.PlumeNoteTestCase;


public class SortNoteTest extends PlumeNoteTestCase {
	
	public SortNoteTest(String name) {
		super(name);
	}
	
	public void testSortAFirst() throws Exception {
		open("/plumenote/notes.app");
		addInitialNotes();
		
		clickElement(NoteTestUtil.SORT_A_FIRST);
		waitFor(4);
		
		List<WebElement> sidebar = getSidebar();
		assertEquals("1st note not properly sorted", "Apple", sidebar.get(0).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("2nd note not properly sorted", "Banana", sidebar.get(1).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("3rd note not properly sorted", "Cat", sidebar.get(2).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("4th note not properly sorted", "Dog", sidebar.get(3).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("5th note not properly sorted", "Elephant", sidebar.get(4).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());	
	}
	
	public void testSortZFirst() throws Exception {
		open("/plumenote/notes.app");
		addInitialNotes();
		
		clickElement(NoteTestUtil.SORT_Z_FIRST);
		waitFor(4);
		
		List<WebElement> sidebar = getSidebar();
		assertEquals("1st note not properly sorted", "Elephant", sidebar.get(0).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("2nd note not properly sorted", "Dog", sidebar.get(1).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("3rd note not properly sorted", "Cat", sidebar.get(2).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("4th note not properly sorted", "Banana", sidebar.get(3).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("5th note not properly sorted", "Apple", sidebar.get(4).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());	
	}
	
	public void testSortOldestFirst() throws Exception {
		open("/plumenote/notes.app");
		
		// Add notes individually to ensure added in specific order
		createNewNote("Banana", "ccc");
		createNewNote("Cat", "aaa");
		createNewNote("Apple", "ddd");
		createNewNote("Dog", "bbb");
		createNewNote("Elephant", "eee");
		
		clickElement(NoteTestUtil.SORT_OLDEST_FIRST);
		waitFor(4);
		
		List<WebElement> sidebar = getSidebar();
		assertEquals("1st note not properly sorted", "Banana", sidebar.get(0).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("2nd note not properly sorted", "Cat", sidebar.get(1).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("3rd note not properly sorted", "Apple", sidebar.get(2).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("4th note not properly sorted", "Dog", sidebar.get(3).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("5th note not properly sorted", "Elephant", sidebar.get(4).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());	
	}
	
	public void testSortNewestFirst() throws Exception {
		open("/plumenote/notes.app");
		
		// Add notes individually to ensure added in specific order
		createNewNote("Banana", "ccc");
		createNewNote("Cat", "aaa");
		createNewNote("Apple", "ddd");
		createNewNote("Dog", "bbb");
		createNewNote("Elephant", "eee");
		
		clickElement(NoteTestUtil.SORT_NEWEST_FIRST);
		waitFor(4);
		
		List<WebElement> sidebar = getSidebar();
		assertEquals("1st note not properly sorted", "Elephant", sidebar.get(0).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("2nd note not properly sorted", "Dog", sidebar.get(1).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("3rd note not properly sorted", "Apple", sidebar.get(2).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("4th note not properly sorted", "Cat", sidebar.get(3).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("5th note not properly sorted", "Banana", sidebar.get(4).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());	
	}
	
	public void testAddNoteWhileSortedAFirst() throws Exception {
		open("/plumenote/notes.app");
		addInitialNotes();
		
		clickElement(NoteTestUtil.SORT_A_FIRST);
		waitFor(4);
		
		clickElement(NoteTestUtil.NEW_NOTE_BUTTON);
		sendText("aaaaa", NoteTestUtil.TITLE_INPUT);
		sendText("aaaaa", NoteTestUtil.BODY_INPUT);
		clickElement(NoteTestUtil.SAVE_BUTTON);		
		waitForSidebarUpdate("First note not added to sidebar", "aaaaa", "aaaaa", true);
		
		List<WebElement> sidebar = getSidebar();
		assertEquals("Note not added to beginning of list", "aaaaa", sidebar.get(0).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
	}
	
	@Ignore("W-1381027")
	public void _testDeleteNoteWhileSorted() throws Exception {
		open("/plumenote/notes.app");
		addInitialNotes();
		
		clickElement(NoteTestUtil.SORT_A_FIRST);
		waitFor(4); 
		
		getElementInSidebar("Cat", "aaa").click();
    	waitForElementAppear(NoteTestUtil.DELETE_BUTTON);
    	clickElement(NoteTestUtil.DELETE_BUTTON);
    	waitForSidebarUpdate("Note never deleted from sidebar", "Cat", "aaa", false);
		
		List<WebElement> sidebar = getSidebar();
		assertEquals("1st note not properly sorted", "Apple", sidebar.get(0).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("2nd note not properly sorted", "Banana", sidebar.get(1).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("4th note not properly sorted", "Dog", sidebar.get(2).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("5th note not properly sorted", "Elephant", sidebar.get(3).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
	}
	
	@Ignore("W-1381027")
	public void _testEditNoteWhileSorted() throws Exception {
		open("/plumenote/notes.app");
		addInitialNotes();
		
		//clickElement(NoteTestUtil.SORT_MENU);
		clickElement(NoteTestUtil.SORT_A_FIRST);
		waitFor(4);
		
		getElementInSidebar("Cat", "aaa").click();
    	waitForElementAppear(NoteTestUtil.EDIT_BUTTON);
    	clickElement(NoteTestUtil.EDIT_BUTTON);
    	waitForElementAppear(NoteTestUtil.TITLE_INPUT);
	    sendText("Dragonfly", NoteTestUtil.TITLE_INPUT);
	    sendText("ddd", NoteTestUtil.BODY_INPUT);
	    clickElement(NoteTestUtil.SAVE_BUTTON);
    	waitForSidebarUpdate("Note never deleted from sidebar", "Dragonfly", "ddd", true);
		
		List<WebElement> sidebar = getSidebar();
		assertEquals("1st note not properly sorted", "Apple", sidebar.get(0).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("2nd note not properly sorted", "Banana", sidebar.get(1).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("3rd note not properly sorted", "Dog", sidebar.get(2).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("4th note not properly sorted", "Dragonfly", sidebar.get(3).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
		assertEquals("5th note not properly sorted", "Elephant", sidebar.get(4).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
	}
	
	@Ignore("W-1381036")
	public void _testChangeSortingWhileAddingNote() throws Exception {
		open("/plumenote/notes.app");
		addInitialNotes();
		
		clickElement(NoteTestUtil.SORT_OLDEST_FIRST);
		waitFor(4);
		
		clickElement(NoteTestUtil.NEW_NOTE_BUTTON);
		sendText("aaaaa", NoteTestUtil.TITLE_INPUT);
		sendText("aaaaa", NoteTestUtil.BODY_INPUT);
		clickElement(NoteTestUtil.SORT_NEWEST_FIRST);
		waitFor(4);
		clickElement(NoteTestUtil.SAVE_BUTTON);		
		waitForSidebarUpdate("First note not added to sidebar", "aaaaa", "aaaaa", true);
		
		List<WebElement> sidebar = getSidebar();
		assertEquals("Note not added to beginning of list", "aaaaa", sidebar.get(0).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText());
	}
	
	public void testSortSameTitleDiffBody() throws Exception {
		open("/plumenote/notes.app");
    	createNewNote("Same Title", "aaa");
    	createNewNote("Same Title", "bbb");
    	createNewNote("Same Title", "ccc");
		
		clickElement(NoteTestUtil.SORT_A_FIRST);
		waitFor(4);
		
		List<WebElement> sidebar = getSidebar();
		assertEquals("1st note not properly sorted", "aaa", sidebar.get(0).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_BODY)).getText().trim());
		assertEquals("2nd note not properly sorted", "bbb", sidebar.get(1).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_BODY)).getText().trim());
		assertEquals("3rd note not properly sorted", "ccc", sidebar.get(2).findElement(By.cssSelector(NoteTestUtil.SIDEBAR_BODY)).getText().trim());
	}
	
	private void addInitialNotes() throws Exception {
		HashMap<String, String> newNotes = new HashMap<String, String>();
		newNotes.put("Cat", "aaa");
    	newNotes.put("Dog", "bbb");
    	newNotes.put("Apple", "ddd");
    	newNotes.put("Banana", "ccc");
    	newNotes.put("Elephant", "eee");
    	createNewNotes(newNotes);
	}

}

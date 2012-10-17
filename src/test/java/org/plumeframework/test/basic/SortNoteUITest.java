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

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.plumeframework.test.PlumeNoteTestUtil;
import org.plumeframework.test.PlumeNoteUITestCase;

public class SortNoteUITest extends PlumeNoteUITestCase {

    public SortNoteUITest(String name) {
        super(name);
    }

    public void testSortAFirst() throws Exception {
        open("/plumenote/notes.app");
        addInitialNotes();

        clickElement(PlumeNoteTestUtil.SORT_A_FIRST);
        waitFor(4);

        checkSidebarOrder(new String[]{"Apple", "Banana", "Cat", "Dog", "Elephant"});
    }

    public void testSortZFirst() throws Exception {
        open("/plumenote/notes.app");
        addInitialNotes();

        clickElement(PlumeNoteTestUtil.SORT_Z_FIRST);
        waitFor(4);

        checkSidebarOrder(new String[]{"Elephant", "Dog", "Cat", "Banana", "Apple"});    
    }

    public void testSortOldestFirst() throws Exception {
        open("/plumenote/notes.app");

        // Add notes individually to ensure added in specific order
        createNewNote("Banana", "ccc");
        createNewNote("Cat", "aaa");
        createNewNote("Apple", "ddd");
        createNewNote("Dog", "bbb");
        createNewNote("Elephant", "eee");

        clickElement(PlumeNoteTestUtil.SORT_OLDEST_FIRST);
        waitFor(4);

        checkSidebarOrder(new String[]{"Banana", "Cat", "Apple", "Dog", "Elephant"});    
    }

    public void testSortNewestFirst() throws Exception {
        open("/plumenote/notes.app");

        // Add notes individually to ensure added in specific order
        createNewNote("Banana", "ccc");
        createNewNote("Cat", "aaa");
        createNewNote("Apple", "ddd");
        createNewNote("Dog", "bbb");
        createNewNote("Elephant", "eee");

        clickElement(PlumeNoteTestUtil.SORT_NEWEST_FIRST);
        waitFor(4);

        checkSidebarOrder(new String[]{"Elephant", "Dog", "Apple", "Cat", "Banana"});
    }

    /**
     * Verifies created note gets added to list in correctly sorted order.
     */
    public void testAddNoteWhileSortedAFirst() throws Exception {
        open("/plumenote/notes.app");
        addInitialNotes();

        clickElement(PlumeNoteTestUtil.SORT_A_FIRST);
        waitFor(4);

        clickElement(PlumeNoteTestUtil.NEW_NOTE_BUTTON);
        sendText("aaaaa", PlumeNoteTestUtil.TITLE_INPUT);
        sendText("aaaaa", PlumeNoteTestUtil.BODY_INPUT);
        clickElement(PlumeNoteTestUtil.SAVE_BUTTON);        
        waitForSidebarUpdate("First note not added to sidebar", "aaaaa", "aaaaa", true);

        List<WebElement> sidebar = getSidebar();
        assertEquals("Note not added to beginning of list", "aaaaa", sidebar.get(0)
                       .findElement(By.cssSelector(PlumeNoteTestUtil.SIDEBAR_TITLE)).getText());
    }

    /**
     * Verifies deleting note from list does not mess up sorted order.
     */
    //"W-1381027"
    public void _testDeleteNoteWhileSorted() throws Exception {
        open("/plumenote/notes.app");
        addInitialNotes();

        clickElement(PlumeNoteTestUtil.SORT_A_FIRST);
        waitFor(4); 

        getElementInSidebar("Cat", "aaa").click();
        waitForElementAppear(PlumeNoteTestUtil.DELETE_BUTTON);
        clickElement(PlumeNoteTestUtil.DELETE_BUTTON);
        waitForSidebarUpdate("Note never deleted from sidebar", "Cat", "aaa", false);

        List<WebElement> sidebar = getSidebar();
        assertEquals("1st note not properly sorted", "Apple", sidebar.get(0)
                        .findElement(By.cssSelector(PlumeNoteTestUtil.SIDEBAR_TITLE)).getText());
        assertEquals("2nd note not properly sorted", "Banana", sidebar.get(1)
                        .findElement(By.cssSelector(PlumeNoteTestUtil.SIDEBAR_TITLE)).getText());
        assertEquals("4th note not properly sorted", "Dog", sidebar.get(2)
                        .findElement(By.cssSelector(PlumeNoteTestUtil.SIDEBAR_TITLE)).getText());
        assertEquals("5th note not properly sorted", "Elephant", sidebar.get(3)
                        .findElement(By.cssSelector(PlumeNoteTestUtil.SIDEBAR_TITLE)).getText());
    }

    /**
     * Verifies editing a note does not mess up sorted order.
     */
    //"W-1381027"
    public void _testEditNoteWhileSorted() throws Exception {
        open("/plumenote/notes.app");
        addInitialNotes();
        
        clickElement(PlumeNoteTestUtil.SORT_A_FIRST);
        waitFor(4);
        
        getElementInSidebar("Cat", "aaa").click();
        waitForElementAppear(PlumeNoteTestUtil.EDIT_BUTTON);
        clickElement(PlumeNoteTestUtil.EDIT_BUTTON);
        waitForElementAppear(PlumeNoteTestUtil.TITLE_INPUT);
        sendText("Dragonfly", PlumeNoteTestUtil.TITLE_INPUT);
        sendText("ddd", PlumeNoteTestUtil.BODY_INPUT);
        clickElement(PlumeNoteTestUtil.SAVE_BUTTON);
        waitForSidebarUpdate("Note never deleted from sidebar", "Dragonfly", "ddd", true);
        
        checkSidebarOrder(new String[]{"Apple", "Banana", "Dog", "Dragonfly", "Elephant"});
    }

    /**
     * Verifies changing sorting between clicking New Note button and clicking Save still saves new note in correctly
     * sorted order.
     */
    //"W-1381036"
    public void _testChangeSortingWhileAddingNote() throws Exception {
        open("/plumenote/notes.app");
        addInitialNotes();
        
        clickElement(PlumeNoteTestUtil.SORT_OLDEST_FIRST);
        waitFor(4);
        
        clickElement(PlumeNoteTestUtil.NEW_NOTE_BUTTON);
        sendText("aaaaa", PlumeNoteTestUtil.TITLE_INPUT);
        sendText("aaaaa", PlumeNoteTestUtil.BODY_INPUT);
        clickElement(PlumeNoteTestUtil.SORT_NEWEST_FIRST);
        waitFor(4);
        clickElement(PlumeNoteTestUtil.SAVE_BUTTON);        
        waitForSidebarUpdate("First note not added to sidebar", "aaaaa", "aaaaa", true);
        
        List<WebElement> sidebar = getSidebar();
        assertEquals("Note not added to beginning of list", "aaaaa", sidebar.get(0)
                        .findElement(By.cssSelector(PlumeNoteTestUtil.SIDEBAR_TITLE)).getText());
    }
    
    public void testSortSameTitleDiffBody() throws Exception {
        open("/plumenote/notes.app");
        createNewNote("Same Title", "aaa");
        createNewNote("Same Title", "bbb");
        createNewNote("Same Title", "ccc");
        
        clickElement(PlumeNoteTestUtil.SORT_A_FIRST);
        waitFor(4);
        
        List<WebElement> sidebar = getSidebar();
        assertEquals("1st note not properly sorted", "aaa", sidebar.get(0)
                        .findElement(By.cssSelector(PlumeNoteTestUtil.SIDEBAR_BODY)).getText().trim());
        assertEquals("2nd note not properly sorted", "bbb", sidebar.get(1)
                        .findElement(By.cssSelector(PlumeNoteTestUtil.SIDEBAR_BODY)).getText().trim());
        assertEquals("3rd note not properly sorted", "ccc", sidebar.get(2)
                        .findElement(By.cssSelector(PlumeNoteTestUtil.SIDEBAR_BODY)).getText().trim());
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
    
    private void checkSidebarOrder(String[] titles) {
        List<WebElement> sidebar = getSidebar();
        for (int i = 0; i < titles.length; i++) {
            assertEquals("Note in position " + i + " of sidebar is not properly sorted", 
                    titles[i], sidebar.get(i).findElement(By.cssSelector(PlumeNoteTestUtil.SIDEBAR_TITLE)).getText());
        }
    }

}

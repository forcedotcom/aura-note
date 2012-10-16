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
package org.plumeframework.test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.plumeframework.demo.notes.DataStore;
import org.plumeframework.demo.notes.Note;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/** 
 * WebDriver test cases for Plume Note.
 *
 */
public class PlumeNoteUITestCase extends WebDriverTestCase {

    public PlumeNoteUITestCase(String name) {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        PlumeNoteTestUtil.saveNotes();
        PlumeNoteTestUtil.clearNotesDb();

        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {    
        PlumeNoteTestUtil.clearNotesDb();
        PlumeNoteTestUtil.restoreSavedNotes();
        PlumeNoteTestUtil.clearCurrNotes();
        
        super.tearDown();
    }

    /**
     * Adds notes to database and refreshes the page.
     *  
     * @param notes Notes to be added to database.
     * @throws SQLException
     */
    protected void createNewNotes(HashMap<String, String> notes) throws SQLException {
        for (String title : notes.keySet()) {
            String body = notes.get(title);
            addNoteDb(title, body);
        }      
        getDriver().navigate().refresh();
        waitForPlumeInit();
    }

    /**
     * Adds a single note to database and refreshes page.
     * 
     * @param title Title of note to be added.
     * @param body Body text of note to be added. 
     * @throws SQLException
     */
    protected void createNewNote(String title, String body) throws SQLException {    
        addNoteDb(title, body);
        getDriver().navigate().refresh();
        waitForPlumeInit();
    }

    private void addNoteDb(String title, String body) throws SQLException {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        Note note = new Note(title, body);
        noteDao.create(note);
    }

    /**
     * Gets an element from the sidebar.
     * 
     * @param  title Title of note to grab.
     * @param  body Body text of note to grab.
     * @return the WebElement in the sidebar with matching title and body, or null if no matching note is found. 
     */
    protected WebElement getElementInSidebar(String title, String body) {
        List<WebElement> sidebar = getSidebar();
        for (int i=0; i<sidebar.size(); i++) {
            WebElement curr = sidebar.get(i);
            String sidebarTitle = curr.findElement(By.cssSelector(PlumeNoteTestUtil.SIDEBAR_TITLE)).getText();
            String sidebarBody = curr.findElement(By.cssSelector(PlumeNoteTestUtil.SIDEBAR_BODY)).getText();
        
            if (title.equals(sidebarTitle) && body.equals(sidebarBody.trim())) {
                return curr;
            }
        }    
        return null;
    }

    protected List<WebElement> getSidebar() {
        List<WebElement> sidebar = getDriver().findElements(By.cssSelector(PlumeNoteTestUtil.SIDEBAR));
        return sidebar;
    }

    protected boolean isInSidebar(String title, String body) {
        WebElement e = getElementInSidebar(title, body);
        return (e != null); 
    }

    protected WebElement getElement(String cssSelector) throws NoSuchElementException {
        return getDriver().findElement(By.cssSelector(cssSelector));
    }

    protected void clickElement(String cssSelector) {
        getElement(cssSelector).click();
    }

    protected void sendText(String text, String cssSelector) {
        WebElement e = getElement(cssSelector);
        e.clear();
        e.sendKeys(text);
    }

    protected String getText(String cssSelector) {
        return getElement(cssSelector).getText();
    }

    protected boolean isElementPresent(String cssSelector) {
        try {
            getElement(cssSelector);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * Waits for sidebar to be updated with a note containing matching title and body text.
     * 
     * @param msg Error message to display on timeout.
     * @param title Title of note.
     * @param body Body text of note.
     * @param appear True if waiting for note of matching title and body to be added to sidebar. False if
     *               if waiting for note to be removed.
     */
    protected void waitForSidebarUpdate(String msg, final String title, final String body, final boolean appear) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeoutInSecs);
        wait.withMessage(msg);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                boolean ret = false;
                try {
                    boolean found = isInSidebar(title, body);
                    ret = appear ? found : !found;
                } catch (StaleElementReferenceException e) {
                    // Here if sidebar updated while still checking contents
                    // Return false, will get new reference next check
                }
                return ret;
            }
        });
    }

    protected void waitForSidebarUpdate(final String title, final String body, final boolean appear) {
        String msg = "Note with title \'" + title + "\' not in Sidebar";
        waitForSidebarUpdate(msg, title, body, appear);
    }

    /**
     * Waits for element with matching CSS selector to appear on screen.
     * 
     * @param msg Error message on timeout.
     * @param cssSelector CSS selector of element waiting for. 
     */
    protected void waitForElementAppear(String msg, final String cssSelector) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeoutInSecs);
        wait.withMessage(msg);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                return isElementPresent(cssSelector);
            }
        });
    }

    protected void waitForElementAppear(String cssSelector) {
        String msg = "Element with CSS selector \'" + cssSelector + "\' never appeared";
        waitForElementAppear(msg, cssSelector);
    }
}

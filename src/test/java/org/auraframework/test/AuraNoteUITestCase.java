/*
 * Copyright (C) 2014 salesforce.com, inc.
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
package org.auraframework.test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.auraframework.demo.notes.DataStore;
import org.auraframework.demo.notes.Note;
import org.auraframework.test.util.WebDriverTestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

/**
 * WebDriver test cases for Aura Note.
 */
public class AuraNoteUITestCase extends WebDriverTestCase {

    public AuraNoteUITestCase(String name) {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        AuraNoteTestUtil.saveDbState();
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        AuraNoteTestUtil.restoreDbState();
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
        auraUITestingUtil.waitForAuraInit();
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
        auraUITestingUtil.waitForAuraInit();
    }

    private void addNoteDb(String title, String body) throws SQLException {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        Note note = new Note(title, body);
        noteDao.create(note);
    }

    /**
     * Gets an element from the sidebar.
     * 
     * @param title Title of note to grab.
     * @param body Body text of note to grab.
     * @return the WebElement in the sidebar with matching title and body, or null if no matching note is found.
     */
    protected WebElement getElementInSidebar(String title, String body) {
        List<WebElement> sidebar = getSidebar();
        for (int i = 0; i < sidebar.size(); i++) {
            WebElement curr = sidebar.get(i);
            String sidebarTitle = curr.findElement(By.cssSelector(AuraNoteTestUtil.SIDEBAR_TITLE)).getText();
            String sidebarBody = curr.findElement(By.cssSelector(AuraNoteTestUtil.SIDEBAR_BODY)).getText();

            if (title.equals(sidebarTitle) && body.equals(sidebarBody.trim())) {
                return curr;
            }
        }
        return null;
    }

    protected List<WebElement> getSidebar() {
        List<WebElement> sidebar = getDriver().findElements(By.cssSelector(AuraNoteTestUtil.SIDEBAR));
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

    protected boolean isLocationButtonEnabled() {
        return getElement(AuraNoteTestUtil.LOCATION_BUTTON).isEnabled();

    }

    protected String getLocationFromNote(String title, String body) {
        return getElement(AuraNoteTestUtil.LOCATION_BUTTON).getAttribute("name");

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
     * @param appear True if waiting for note of matching title and body to be added to sidebar. False if waiting for
     *            note to be removed.
     */
    protected void waitForSidebarUpdate(String msg, final String title, final String body, final boolean appear) {
        WebDriverWait wait = new WebDriverWait(getDriver(), auraUITestingUtil.getTimeout());
        wait.withMessage(msg);
        // StaleElementReferenceException gets thrown when sidebar is updated while we're checking, ignore the exception
        // and look again until we timeout.
        wait.ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                boolean found = isInSidebar(title, body);
                return appear ? found : !found;
            }
        });
    }

    protected void waitForSidebarUpdate(final String title, final String body, final boolean appear) {
        String msg = "Note with title \'" + title + "\' not in Sidebar";
        waitForSidebarUpdate(msg, title, body, appear);
    }

    /**
     * Verify note in sidebar is located in index.
     * 
     * @param msg Error message to display to user if wait times out
     * @param title Title of note to check for in sidebar
     * @param index Index note should be sorted into in sidebar, 0 being the first note at the top of the sidebar.
     */
    protected void waitForSidebarSortByTitle(String msg, final String title, final int index) {
        WebDriverWait wait = new WebDriverWait(getDriver(), auraUITestingUtil.getTimeout());
        wait.withMessage(msg);
        // StaleElementReferenceException gets thrown when sidebar is updated while we're checking, ignore the exception
        // and look again until we timeout.
        wait.ignoring(StaleElementReferenceException.class);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                String t = getSidebar().get(index).findElement(By.cssSelector(AuraNoteTestUtil.SIDEBAR_TITLE))
                        .getText();
                return t.equals(title);
            }
        });
    }

    /**
     * Waits for element with matching CSS selector to appear on screen.
     * 
     * @param msg Error message on timeout.
     * @param cssSelector CSS selector of element waiting for.
     */
    protected void waitForElementAppear(String msg, final String cssSelector) {
        WebDriverWait wait = new WebDriverWait(getDriver(), auraUITestingUtil.getTimeout());
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

    protected void waitForTextChange(String errorMsg, final String selector, final String newText) {
        WebDriverWait wait = new WebDriverWait(getDriver(), auraUITestingUtil.getTimeout());
        wait.withMessage(errorMsg);
        wait.ignoring(NoSuchElementException.class);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                String t = getElement(selector).getText();
                return t.equals(newText);
            }
        });
    }
}

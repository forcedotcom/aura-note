package org.plumeframework.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.plumeframework.demo.notes.DataStore;
import org.plumeframework.demo.notes.Note;
import org.plumeframework.test.WebDriverTestCase;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class PlumeNoteTestCase extends WebDriverTestCase {
	private List<Note> notes = new ArrayList<Note>();
	
	public PlumeNoteTestCase(String name) {
		super(name);
	}
	
	@Override
    public void setUp() throws Exception {
		saveNotes();
		clearNotesDb();
		
    	super.setUp();
    }
	
    @Override
    public void tearDown() throws Exception {	
    	clearNotesDb();
    	restoreSavedNotes();
    	notes.clear();
    	
        super.tearDown();
    }
    
    private void saveNotes() throws SQLException {
		Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
		CloseableIterator<Note> iter = noteDao.iterator();
		while (iter.hasNext()) {
			Note curr = iter.next();
			notes.add(curr);
		}
    }
    
    private void restoreSavedNotes() throws SQLException {
    	Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
    	for (Note note : notes) {
    		noteDao.create(note);
    	}
    }
    
    private void clearNotesDb() throws SQLException {
    	Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
    	CloseableIterator<Note> iter = noteDao.iterator();
		while (iter.hasNext()) {
			Note curr = iter.next();
			noteDao.deleteById(curr.getId());
		}
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
    		String sidebarTitle = curr.findElement(By.cssSelector(NoteTestUtil.SIDEBAR_TITLE)).getText();
    		String sidebarBody = curr.findElement(By.cssSelector(NoteTestUtil.SIDEBAR_BODY)).getText();
		
    		if (title.equals(sidebarTitle) && body.equals(sidebarBody.trim())) {
    			return curr;
    		}
    	}	
    	return null;
    }
    
    protected List<WebElement> getSidebar() {
		List<WebElement> sidebar = getDriver().findElements(By.cssSelector(NoteTestUtil.SIDEBAR));
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

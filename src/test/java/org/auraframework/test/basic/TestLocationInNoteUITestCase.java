package org.auraframework.test.basic;

import org.auraframework.demo.notes.Note;
import org.auraframework.test.AuraNoteTestUtil;
import org.auraframework.test.AuraNoteUITestCase;

public class TestLocationInNoteUITestCase extends AuraNoteUITestCase {

    public TestLocationInNoteUITestCase(String name) {
        super(name);
    }

    public void testAddLocation() throws Exception {
        open("/auranote/notes.app");

        Note note = new Note("Hello World", "I am Mr. Roboto");

        // Create first note
        clickElement(AuraNoteTestUtil.NEW_NOTE_BUTTON);
        waitForElementAppear(AuraNoteTestUtil.TITLE_INPUT);
        clickElement(AuraNoteTestUtil.LOCATION_BUTTON);
        clickElement(AuraNoteTestUtil.TITLE_INPUT);
        sendText(note.getTitle(), AuraNoteTestUtil.TITLE_INPUT);
        sendText(note.getBody(), AuraNoteTestUtil.BODY_INPUT);
        String latlng = getText(AuraNoteTestUtil.LOCATION_BUTTON);
        assertFalse("Location is Enabled ", isLocationButtonEnabled());

        clickElement(AuraNoteTestUtil.SAVE_BUTTON);
        waitForSidebarUpdate("First note not added to sidebar", note.getTitle(), note.getBody(), true);

        // Check details view of first Note
        getElementInSidebar(note.getTitle(), note.getBody()).click();
        waitForTextChange("Details view title does not match new note", AuraNoteTestUtil.DETAILS_TITLE, note.getTitle());
        waitForTextChange("Details view body does not match new note", AuraNoteTestUtil.DETAILS_BODY, note.getBody());
        assertEquals("Details view location does not match new note", getText(AuraNoteTestUtil.LOCATION_BUTTON), latlng);
    }
}

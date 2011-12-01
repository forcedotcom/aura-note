package org.lumenframework.demo.notes.controllers;

import org.lumenframework.demo.notes.HelloNotes;

import lumen.system.Annotations.Controller;
import lumen.system.Annotations.LumenEnabled;

@Controller
public class NoteListController {
	@LumenEnabled
    public static void createNoteAction() throws Exception {
		HelloNotes.main(null);
	}
}

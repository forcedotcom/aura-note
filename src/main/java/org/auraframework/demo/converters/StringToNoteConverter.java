package org.auraframework.demo.converters;

import org.auraframework.demo.notes.Note;
import org.auraframework.util.type.Converter;

public class StringToNoteConverter implements Converter<String, Note> {

	/**
	 * everytime we send in a string to a note attribute, we will go to this converter
	 * expected input: "title:SOMETHING HERE, body:SOMETHING ELSE HERE"
	 */
	@Override
	public Note convert(String titleAndBody) {
		String []  str =  titleAndBody.split(",");
		
		str[0] = str[0].split(":")[1];
		str[1] = str[1].split(":")[1];
		
		return new Note(str[0], str[1]);
	}

	@Override
	public Class<String> getFrom() {
		return String.class;
	}

	@Override
	public Class<Note> getTo() {
		return Note.class;
	}

	@Override
	public Class<?>[] getToParameters() {
		return null;
	}

}

package org.lumenframework.demo.notes;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "note")
public class Note {
	
	@DatabaseField(generatedId = true)
	private long id;

	@DatabaseField
	private String title;
	
	@DatabaseField
	private String body;

	public Note() {
		// ORMLite needs a no-arg constructor
	}

	public Note(String title, String body) {
		this.title = title;
		this.body = body;
	}
	
	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title == null?"":body;
	}

	public String getBody() {
		return body == null?"":body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	@Override
	public String toString() {
		return String.format("\n#%s: %s \n\t%s", getId(), getTitle(), getBody());
	}
}
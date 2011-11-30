package org.lumenframework.demo.notes;

import java.io.IOException;

import lumen.util.json.Json;
import lumen.util.json.JsonSerializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "note")
public class Note implements JsonSerializable {
	
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
		this.title = title;
	}

	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	@Override
	public String toString() {
		return String.format("\n#%s: %s \n\t%s", getId(), title==null?"":title, body==null?"":body);
	}

	@Override
    public void serialize(Json json) throws IOException {
        json.writeMapBegin();
        json.writeMapEntry("id", getId());
        json.writeMapEntry("title", getTitle());
        json.writeMapEntry("body", getBody());
        json.writeMapEnd();
    }
}
package org.lumenframework.demo.notes;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import lumen.util.json.Json;
import lumen.util.json.JsonSerializable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "note")
public class Note implements JsonSerializable {
	
	@DatabaseField(generatedId = true)
	private long id;

	@DatabaseField
	private String title;

	@DatabaseField(dataType=DataType.LONG_STRING)
	private String body;

	@DatabaseField(dataType=DataType.DATE)
	private Date createdOn;
	
	@DatabaseField
	private Double latitude;
	
	@DatabaseField
	private Double longitude;
	
	public Note() {
		// ORMLite needs a no-arg constructor
		this.createdOn = Calendar.getInstance().getTime();
	}

	public Note(String title, String body) {
		this();
		
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
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Calendar getCreatedOnAsCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(createdOn);
		return calendar;
	}

	@Override
	public String toString() {
		return String.format("\n#%s: %s \n\t%s", getId(), title == null ? "" : title, body == null ? "" : body);
	}

	@Override
	public void serialize(Json json) throws IOException {
		json.writeMapBegin();
		json.writeMapEntry("id", getId());
		json.writeMapEntry("title", getTitle());
		json.writeMapEntry("body", getBody());
		json.writeMapEntry("latitude", getLatitude());
		json.writeMapEntry("longitude", getLongitude());
		json.writeMapEntry("createdOn", getCreatedOnAsCalendar());
		json.writeMapEnd();
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLatitude() {
		return latitude;
	}
}
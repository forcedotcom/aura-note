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
package org.auraframework.demo.notes;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.auraframework.util.json.Json;
import org.auraframework.util.json.JsonSerializable;

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
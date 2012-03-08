package org.lumenframework.demo.notes.controllers;

import java.math.BigDecimal;
import java.util.Map;

import lumen.Lumen;
import lumen.def.ComponentDef;
import lumen.instance.Component;
import lumen.system.Annotations.Controller;
import lumen.system.Annotations.Key;
import lumen.system.Annotations.LumenEnabled;

import org.lumenframework.demo.notes.persist.PersistableNoteH2Impl;

import com.google.common.collect.Maps;

@Controller
public class NoteEditController {

	@LumenEnabled
    public static Component saveNote(@Key("id")Long id,
    								 @Key("title")String title,
    								 @Key("body")String body,
    								 @Key("sort")String sort,
    								 @Key("latitude")BigDecimal latitude,
    								 @Key("longitude")BigDecimal longitude) throws Exception {

		PersistableNoteH2Impl persistImpl = new PersistableNoteH2Impl();
		persistImpl.saveNote(id, title, body, latitude, longitude);

		Map<String, Object> listAttributes = Maps.newHashMap();
		listAttributes.put("sort", sort);

		return Lumen.getInstanceService().getInstance("lumennote:noteList", ComponentDef.class, listAttributes);
	}
}

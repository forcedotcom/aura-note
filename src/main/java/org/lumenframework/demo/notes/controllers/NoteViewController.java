package org.lumenframework.demo.notes.controllers;

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
public class NoteViewController {

    @LumenEnabled
    public static Component deleteNote(@Key("id")Long id, @Key("sort")String sort) throws Exception {
		PersistableNoteH2Impl persistImpl = new PersistableNoteH2Impl();
		persistImpl.deleteById(id);

        Map<String, Object> listAttributes = Maps.newHashMap();
        listAttributes.put("sort", sort);
        return Lumen.getInstanceService().getInstance("lumennote:noteList", ComponentDef.class, listAttributes);
    }
}

/*
 * Copyright (C) 2012 salesforce.com, inc.
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
package org.auraframework.demo.notes.controllers;

import org.auraframework.demo.notes.DataStore;
import org.auraframework.demo.notes.Note;
import org.auraframework.system.Annotations.AuraEnabled;
import org.auraframework.system.Annotations.Controller;
import org.auraframework.system.Annotations.Key;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

@Controller
public class NoteViewController {

    @AuraEnabled
    public static void deleteNote(@Key("id") Long id, @Key("sort") String sort) throws Exception {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        noteDao.deleteById(id);
        return;
    }
}

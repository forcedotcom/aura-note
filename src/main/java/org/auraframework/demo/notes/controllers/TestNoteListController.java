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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.auraframework.demo.notes.DataStore;
import org.auraframework.demo.notes.Note;
import org.auraframework.system.Annotations.AuraEnabled;
import org.auraframework.system.Annotations.Controller;
import org.auraframework.system.Annotations.Key;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;

@Controller
public class TestNoteListController {

	private static String replaceAllRegex(String source, String pattern, String replacement) {
	    if (source == null)
	          return null;
	    Pattern regex = Pattern.compile(pattern);
	    Matcher matcher = regex.matcher(source);
	    return matcher.replaceAll(replacement);
	}
	
    @AuraEnabled
    public static void deleteNotesByKey(@Key("key") String key) throws Exception {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        List<Long> ids = Lists.newArrayList();
        GenericRawResults<String[]> searchResults = noteDao.queryRaw("SELECT KEYS FROM FT_SEARCH_DATA(?,0,0)", key);
        try {
            for (String[] row : searchResults) {
                ids.add(Long.parseLong(replaceAllRegex(row[0], "[()]", "")));
            }
        } finally {
            searchResults.close();
        }
        noteDao.deleteIds(ids);
    }
}

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
package org.auraframework.demo.notes.models;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.auraframework.Aura;
import org.auraframework.demo.notes.DataStore;
import org.auraframework.demo.notes.Note;
import org.auraframework.instance.BaseComponent;
import org.auraframework.system.Annotations.AuraEnabled;
import org.auraframework.system.Annotations.Model;
import org.auraframework.util.AuraTextUtil;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;

enum SortCol {
    title, createdOn
};

enum SortDir {
    asc, desc
}

@Model
public class NoteListModel {

    private static DataStore dataStore = DataStore.getInstance();
    private List<Note> notes;

    private static String replaceAllRegex(String source, String pattern, String replacement) {
        if (source == null) return null;
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(source);
        return matcher.replaceAll(replacement);
    }

    public NoteListModel() throws Exception {
        Dao<Note, Long> noteDao = dataStore.getNoteDao();

        BaseComponent<?, ?> cmp = Aura.getContextService().getCurrentContext().getCurrentComponent();

        List<String> sortSplit = AuraTextUtil.splitSimple(".", (String)cmp.getAttributes().getValue("sort"));

        SortCol sortCol = SortCol.createdOn;
        SortDir sortDir = SortDir.desc;

        if (sortSplit != null) {
            sortCol = SortCol.valueOf(sortSplit.get(0));
            sortDir = SortDir.valueOf(sortSplit.get(1));
        }

        String query = (String)cmp.getAttributes().getValue("query");
        QueryBuilder<Note, Long> qb = noteDao.queryBuilder();
        if (!AuraTextUtil.isNullEmptyOrWhitespace(query)) {
            List<Long> ids = Lists.newArrayList();
            GenericRawResults<String[]> searchResults = noteDao.queryRaw("SELECT KEYS FROM FT_SEARCH_DATA(?,0,0)",
                    query);
            try {
                for (String[] row : searchResults) {
                    ids.add(Long.parseLong(replaceAllRegex(row[0], "[()]", "")));
                }
            } finally {
                searchResults.close();
            }

            qb.setWhere(qb.where().in("id", ids));
        }

        qb.orderBy(sortCol.name(), sortDir == SortDir.asc);
        qb.limit(100L);

        notes = qb.query();

        if (notes.isEmpty()) {
            notes.add(new Note("Sample Note", "Just a simple note to let you know <h1>Aura</h1> loves you!"));
        }
    }

    @AuraEnabled
    public List<Note> getNotes() {
        return notes;
    }

}

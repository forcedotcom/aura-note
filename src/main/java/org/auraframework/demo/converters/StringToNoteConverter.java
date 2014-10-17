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
package org.auraframework.demo.converters;

import org.auraframework.demo.notes.Note;
import org.auraframework.util.type.Converter;

public class StringToNoteConverter implements Converter<String, Note> {

    /**
     * everytime we send in a string to a note attribute, we will go to this converter expected input:
     * "title:SOMETHING HERE, body:SOMETHING ELSE HERE"
     */
    @Override
    public Note convert(String titleAndBody) {
        String[] str = titleAndBody.split(",");

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

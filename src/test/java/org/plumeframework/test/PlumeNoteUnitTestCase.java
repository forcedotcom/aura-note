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
package org.plumeframework.test;

import junit.framework.TestCase;

import org.plumeframework.Plume;
import org.plumeframework.system.PlumeContext.Access;
import org.plumeframework.system.PlumeContext.Format;
import org.plumeframework.system.PlumeContext.Mode;

/**
 * Unit tests for Plume Note. Establishes and tears down context for running tests.
 *
 */
public class PlumeNoteUnitTestCase extends TestCase {

    @Override
    public void setUp() throws Exception {
        if (!Plume.getContextService().isEstablished()) {
            Plume.getContextService().startContext(Mode.UTEST, Format.JSON, Access.AUTHENTICATED);
        }
        PlumeNoteTestUtil.saveNotes();
        PlumeNoteTestUtil.clearNotesDb();
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        if (!Plume.getContextService().isEstablished()) {
            Plume.getContextService().endContext();
        }
        PlumeNoteTestUtil.clearNotesDb();
        PlumeNoteTestUtil.restoreSavedNotes();
        PlumeNoteTestUtil.clearCurrNotes();
        super.tearDown();
    }
}

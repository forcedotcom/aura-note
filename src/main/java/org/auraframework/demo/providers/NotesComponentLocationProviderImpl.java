/*
 * Copyright (C) 2013 salesforce.com, inc.
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
package org.auraframework.demo.providers;

import org.auraframework.adapter.ComponentLocationAdapter.Impl;
import org.auraframework.ds.log.AuraDSLog;
import org.auraframework.ds.serviceloader.AuraServiceProvider;
import org.auraframework.provider.api.ComponentPackageProvider;

import aQute.bnd.annotation.component.Component;

@Component (provide={AuraServiceProvider.class, ComponentPackageProvider.class})
public class NotesComponentLocationProviderImpl  extends Impl implements ComponentPackageProvider {
	private static final String NOTES_APPLICATION_AURA_COMPONENTS_PACKAGE = "notes_application_aura_components";
	public NotesComponentLocationProviderImpl() {
		super(NOTES_APPLICATION_AURA_COMPONENTS_PACKAGE);
    	AuraDSLog.get().info("Instantiated " + getClass().getSimpleName());
	}
}



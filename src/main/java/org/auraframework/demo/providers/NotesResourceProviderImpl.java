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

import java.io.IOException;
import java.net.URL;

import org.auraframework.provider.api.BundleResourceProvider;
import org.osgi.framework.Bundle;
import org.osgi.service.component.ComponentContext;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;

@Component (provide=BundleResourceProvider.class)
public class NotesResourceProviderImpl implements BundleResourceProvider {
	
	// TODO: osgi We could avoid holding onto bundle reference and just make a note of it 
	// when adding provider instance to core factory
	private Bundle bundle;

	@Activate
	protected void activate(ComponentContext componentContext) {
		this.bundle = componentContext.getBundleContext().getBundle();
	}
	
	@Override
	public URL getBundleResource(String resource) throws IOException {
		return bundle != null ? bundle.getEntry(resource) : null;
	}
}

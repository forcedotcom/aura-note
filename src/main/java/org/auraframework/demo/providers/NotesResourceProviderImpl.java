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

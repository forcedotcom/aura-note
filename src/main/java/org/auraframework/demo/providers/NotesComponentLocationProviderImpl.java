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



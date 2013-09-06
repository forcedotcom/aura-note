package org.auraframework.demo.providers;

import org.auraframework.provider.api.ClassProvider;

import aQute.bnd.annotation.component.Component;

@Component
public class NotesClassProviderImpl implements ClassProvider {

	@Override
	public Class<?> getClazzForName(String className) throws ClassNotFoundException {
		return Class.forName(className);
	}
}

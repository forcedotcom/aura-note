package org.auraframework.demo.providers;

import org.auraframework.def.DefDescriptor;
import org.auraframework.def.SecurityProvider;
import org.auraframework.ds.log.AuraDSLog;

public class NotesSecurityProvider  implements SecurityProvider {
	
	public NotesSecurityProvider() {
    	AuraDSLog.get().info("Instantiated " + getClass().getSimpleName());
	}
	
	@Override
	public boolean isAllowed(DefDescriptor<?> descriptor) {
		// TODO: osgi-fixme need to define how to deal with security provider
//		return !Aura.getConfigAdapter().isProduction();
		return true;
	}
}
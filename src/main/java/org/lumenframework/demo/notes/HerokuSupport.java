package org.lumenframework.demo.notes;

import lumen.util.LumenFiles;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class HerokuSupport {
    public static void main(String[] args) throws Exception{
    	Server server = new Server(Integer.valueOf(System.getenv("PORT")));
    	
    	WebAppContext context = new WebAppContext();
    	context.setDescriptor(null);
        context.setResourceBase(LumenFiles.Htdocs.getPath());
        context.setContextPath("/");
        context.setParentLoaderPriority(true);
        
        server.setHandler(context);
        
        server.start();
        server.join();             
    }
}

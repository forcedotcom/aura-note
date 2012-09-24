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
package configuration;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.plumeframework.test.*;
import org.plumeframework.test.configuration.JettyTestServletConfig;
import org.plumeframework.test.configuration.TestServletConfig;
import org.plumeframework.util.ServiceLoaderImpl.Impl;
import org.plumeframework.util.ServiceLoaderImpl.PlumeConfiguration;



@PlumeConfiguration
public class PlumeIntegrationTestConfig {
	@Impl
    public static TestServletConfig plumeJettyServletTestInfo() throws Exception {
        return new JettyTestServletConfig();
    }
	
    @Impl
    public static WebDriverProvider plumeWebDriverProvider() throws Exception {
        URL serverUrl;
        try {
            String hubUrlString = System.getProperty(WebDriverProvider.WEBDRIVER_SERVER_PROPERTY);
            if ((hubUrlString != null) && !hubUrlString.equals("")) {
                serverUrl = new URL(hubUrlString);
            } else {
                int serverPort = Integer.parseInt(System.getProperty("selenium.server.port", "4444"));

                // quiet the verbose grid logging
                Logger selLog = Logger.getLogger("org.openqa");
                selLog.setLevel(Level.SEVERE);

                SeleniumServerLauncher.start("localhost", serverPort);
                serverUrl = new URL(String.format("http://localhost:%s/wd/hub", serverPort));
                System.setProperty(WebDriverProvider.WEBDRIVER_SERVER_PROPERTY, serverUrl.toString());
            }
            Logger.getLogger(PlumeIntegrationTestConfig.class.getName()).info("Selenium server url: " + serverUrl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e);
        }
        if (Boolean.parseBoolean(System.getProperty(WebDriverProvider.REUSE_BROWSER_PROPERTY))) {
            return new PooledRemoteWebDriverFactory(serverUrl);
        } else {
            return new RemoteWebDriverFactory(serverUrl);
        }
    }
}

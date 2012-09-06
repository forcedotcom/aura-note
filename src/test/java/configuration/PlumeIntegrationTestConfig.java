package configuration;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.plumeframework.test.PooledRemoteWebDriverFactory;
import org.plumeframework.test.RemoteWebDriverFactory;
import org.plumeframework.test.SauceUtil;
import org.plumeframework.test.SeleniumServerLauncher;
import org.plumeframework.test.WebDriverProvider;
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
        boolean runningOnSauceLabs = SauceUtil.areTestsRunningOnSauce();
        try {
            String hubUrlString = System.getProperty(WebDriverProvider.WEBDRIVER_SERVER_PROPERTY);
            if ((hubUrlString != null) && !hubUrlString.equals("")) {
                if (runningOnSauceLabs)
                    serverUrl = SauceUtil.getSauceServerUrl();
                else
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
        if (!runningOnSauceLabs && Boolean.parseBoolean(System.getProperty(WebDriverProvider.REUSE_BROWSER_PROPERTY))) {
            return new PooledRemoteWebDriverFactory(serverUrl);
        } else {
            return new RemoteWebDriverFactory(serverUrl);
        }
    }
}

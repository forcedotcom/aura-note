package org.plumeframework.test;

import java.net.Socket;

import org.apache.log4j.Logger;
import org.openqa.grid.selenium.GridLauncher;

/**
 * Get WebDriver instances for Plume tests.
 *
 * @author gtorres
 * @since 0.0.94
 */
public class SeleniumServerLauncher {
    private static String browsers = "-browser browserName=chrome,maxInstances=4 -browser browserName=firefox,maxInstances=4";
	
    public static void main(String args[]) throws Exception {
        final String host = "localhost";
        final int serverPort = Integer.parseInt(args[0]);
        start(host, serverPort);
    }

    public static void start(String host, int serverPort) throws Exception {
        Logger logger = Logger.getLogger(SeleniumServerLauncher.class.getName());

        logger.info("Launching Selenium server on port " + serverPort);
        GridLauncher.main(String.format("-port %s %s", serverPort, browsers).split(" "));
        logger.info("Waiting for server to open port");
        waitForServer(host, serverPort);

        // Don't need to startup a grid as of now
        //
        // logger.info("Launching Selenium grid hub on port " + serverPort);
        // GridLauncher.main(String.format("-port %s -role hub", serverPort).split(" "));
        // logger.info("Waiting for hub to open port");
        // waitForPortOpen(host, serverPort);
        //
        // logger.info("Launching Selenium grid node on port " + nodePort);
        // logger.info("chrome driver location: " + System.getProperty("webdriver.chrome.driver"));
        // GridLauncher.main(String.format("-port %s -role webdriver -hub http://%s:%s/grid/register %s", nodePort,
        // host, serverPort, browsers).split(" "));
        // logger.info("Waiting for node to open port");
        // waitForPortOpen(host, nodePort);
        // Thread.sleep(1000);
    }

    // just check if port is listening
    private static void waitForServer(String host, int port) {
        boolean isUp = false;
        for (int tries = 0; !isUp && tries < 10; tries++) {
            try {
                new Socket(host, port);
                isUp = true;
            } catch (Exception e) {}
            ;
        }
        if (!isUp) { throw new Error(String.format("Failed to open socket to port %d", port)); }
    }
}

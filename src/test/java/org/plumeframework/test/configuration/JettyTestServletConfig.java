/*
 * Copyright, 1999-20011, salesforce.com All Rights Reserved Company Confidential
 */
package org.plumeframework.test.configuration;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.commons.httpclient.HttpClient;
import org.plumeframework.Plume;
import org.plumeframework.test.configuration.TestServletConfig;

/**
 * @author gtorres
 * @since 0.0.59
 */
public class JettyTestServletConfig implements TestServletConfig {
    private URL baseUrl;

    public JettyTestServletConfig() throws MalformedURLException, URISyntaxException {
        int port = Integer.parseInt(System.getProperty("jetty.port", "8080"));
        String host = System.getProperty("jetty.host");
        if (host == null) {
            try {
                host = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                host = "localhost";
            }
        }
        baseUrl = new URL("http", host, port, "/");
    }

    @Override
    public URL getBaseUrl() {
        return baseUrl;
    }

    @Override
    public HttpClient getHttpClient() {
        // 10 minute timeout for making a connection and for waiting for data on the connection.
        // This prevents tests from hanging in the http code, which in turn can prevent the server from exiting.
        int timeout = 10 * 60 * 1000;
        HttpClient http = new HttpClient();
        http.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
        http.getParams().setSoTimeout(timeout);
        return http;
    }

    @Override
    public String getCsrfToken() throws Exception {
        String token = Plume.getConfigAdapter().getCSRFToken();
        return token == null ? "" : token;
    }
}

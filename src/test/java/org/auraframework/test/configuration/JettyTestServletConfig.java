/*
 * Copyright (C) 2014 salesforce.com, inc.
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
package org.auraframework.test.configuration;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.auraframework.Aura;

public class JettyTestServletConfig implements TestServletConfig {
    private final URL baseUrl;

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
        // timeout for making a connection and for waiting for data on the connection.
        // this prevents tests from hanging in the http code, which in turn can
        // prevent the server from exiting.
        int timeout = (int) TimeUnit.MINUTES.toSeconds(10);

        CookieStore cookieStore = new BasicCookieStore();
        
        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder = requestBuilder.setConnectTimeout(timeout);
        requestBuilder = requestBuilder.setSocketTimeout(timeout);

        HttpClientBuilder builder = HttpClientBuilder.create();     
        builder.setDefaultRequestConfig(requestBuilder.build());
        builder.setDefaultCookieStore(cookieStore);
        HttpClient client = builder.build();

        return client;
    }

    @Override
    public String getCsrfToken() throws Exception {
        String token = Aura.getConfigAdapter().getCSRFToken();
        return token == null ? "" : token;
    }
    
}

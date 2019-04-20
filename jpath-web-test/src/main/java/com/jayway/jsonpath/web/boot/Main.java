/*
 * Copyright 2011 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This source code was originally provided by the Jayway JsonPath project
 * and might have been modified in the JPath project
 */
package com.jayway.jsonpath.web.boot;

import com.jayway.jsonpath.web.resource.ApiResource;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.io.IOException;



public class Main {

    public static void main(String[] args) throws Exception {
        String configPort = "8080";
        if(args.length > 0){
            configPort = args[0];
        }

        String port = System.getProperty("server.http.port", configPort);
        System.out.println("Server started on port: " + port);

        Server server = new Server();

        server.setConnectors(new Connector[]{createConnector(server, Integer.parseInt(port))});

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/api");
        ServletHolder servletHolder = new ServletHolder(createJerseyServlet());
        servletHolder.setInitOrder(1);
        context.addServlet(servletHolder, "/*");

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setServer(server);
        webAppContext.setContextPath("/");

        String resourceBase = System.getProperty("resourceBase");
        if(resourceBase != null){
            webAppContext.setResourceBase(resourceBase);
        } else {
            webAppContext.setResourceBase(Main.class.getResource("/webapp").toExternalForm());
        }

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{context, webAppContext});
        server.setHandler(handlers);

        server.start();
        server.join();
    }

    private static ServerConnector createConnector(Server s, int port){
        ServerConnector connector = new ServerConnector(s);
        connector.setHost("0.0.0.0");
        connector.setPort(port);
        return connector;
    }

    private static ServletContainer createJerseyServlet() throws IOException {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(JacksonFeature.class);

        resourceConfig.register(new ApiResource());

        return new ServletContainer(resourceConfig);
    }
}

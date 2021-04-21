package com.thing.runtime;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    public static Properties configProperties;

    private static HttpServer restServer;

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     */
    public static void startServer() throws IOException {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example.rest package
        final ResourceConfig rc = new ResourceConfig().packages("com.thing.rest").register(MultiPartFeature.class);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        restServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        System.out.printf("Jersey app started with WADL available at "
            + "%sapplication.wadl\n\n\n", BASE_URI);
    }

    private static void stopServer() {
        restServer.shutdownNow();
    }

    public static void main(String[] args) throws IOException {
        configProperties = new Properties();

        ClassLoader classLoader = Main.class.getClassLoader();

        InputStream config = classLoader.getResourceAsStream("config.properties");
        configProperties.load(config);

        startServer();

        Scanner kb = new Scanner(System.in);
        kb.nextLine();

        stopServer();
    }
}


package com.thing.runtime;

import com.thing.soap.LogServiceImpl;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.jaxws.JaxwsHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    private static final int SOAP_PORT = 8090;
    private static final String SOAP_SERVICE_PATH = "/logs";

    private static HttpServer restServer;
    private static HttpServer soapServer;

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



        NetworkListener networkListener = new NetworkListener("jaxws-listener", "0.0.0.0", SOAP_PORT);
        HttpHandler httpHandler = new JaxwsHandler(new LogServiceImpl());

        soapServer = new HttpServer();
        soapServer.getServerConfiguration().addHttpHandler(httpHandler, SOAP_SERVICE_PATH);
        soapServer.addListener(networkListener);
        soapServer.start();

        String BASE_URI_SOAP = "http://localhost:" + SOAP_PORT + SOAP_SERVICE_PATH;
        System.out.println("SOAP Service listening on " + BASE_URI_SOAP + "?wsdl");
    }

    private static void stopServer() {
        restServer.shutdownNow();
        soapServer.shutdownNow();
    }

    public static void main(String[] args) throws IOException {
        startServer();

        Scanner kb = new Scanner(System.in);
        kb.nextLine();

        stopServer();
    }
}


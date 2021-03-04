package com.thing.runtime;

import com.thing.core.Album;
import com.thing.core.Artist;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.net.URI;
import java.net.URL;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/myapp/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example.rest package
        final ResourceConfig rc = new ResourceConfig().packages("com.thing.rest");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

//    public static void main(String[] args) throws IOException {
//        final HttpServer server = startServer();
//
//        System.out.printf("Jersey app started with WADL available at "
//                + "%sapplication.wadl\n\n\n", BASE_URI);
//
//        Scanner kb = new Scanner(System.in);
//        kb.nextLine();
//
//        server.stop();
//    }

    public static void main(String[] args) {
//        URL url = Thread.currentThread().getContextClassLoader()
//            .getResource("javax/persistence/Table.class");
//        System.out.println(url);
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Album e1=new Album();
        e1.setYear(434);
        e1.setIsrc("Chawla");
        e1.setTitle("sd");
        Artist e23 = new Artist();
        e23.setFirstName("dssd");
        e23.setLastName("eed");
        e1.setArtist(e23);

        session.save(e1);
        t.commit();
        System.out.println("successfully saved");
        factory.close();
        session.close();

    }
}


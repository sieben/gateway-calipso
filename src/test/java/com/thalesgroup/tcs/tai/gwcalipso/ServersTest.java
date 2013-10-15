package com.thalesgroup.tcs.tai.gwcalipso;

import ch.ethz.inf.vs.californium.endpoint.LocalEndpoint;
import ch.ethz.inf.vs.californium.examples.ExampleServer;
import ch.ethz.inf.vs.californium.examples.resources.HelloWorldResource;
import ch.ethz.inf.vs.californium.examples.resources.TimeResource;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ServersTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // create a local CoAP server
        LocalEndpoint coap_server = new ExampleServer();
        coap_server.addResource(new HelloWorldResource());
        coap_server.addResource(new TimeResource());
        coap_server.start();


        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testServers(){
        String responseMsg = target.path("servers").request().get(String.class);
        System.out.println(responseMsg);
    }

//    @Test
//    public void testLocalServer(){
//        String responseMsg = target.path("servers/localhost").request().get(String.class);
//        System.out.println(responseMsg);
//    }

    @Test
    public void testRemoteServer() {
     String responseMsg = target.path("servers/coap.me").request().get(String.class);
     System.out.println(responseMsg);
    }


}
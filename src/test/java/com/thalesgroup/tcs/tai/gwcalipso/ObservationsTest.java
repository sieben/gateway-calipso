package com.thalesgroup.tcs.tai.gwcalipso;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.server.resources.CoapExchange;


import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ObservationsTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // create a local CoAP server
        CoapServer coap_server = new CoapServer();
        coap_server.add(new HelloWorldResource());
  //      coap_server.addResource(new TimeResource());
        coap_server.start();


        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

class HelloWorldResource extends CoapResource {
        
        public HelloWorldResource() {
            
            // set resource identifier
            super("helloWorld");
            
            // set display name
            getAttributes().setTitle("Hello-World Resource");
        }
        
        @Override
        public void handleGET(CoapExchange exchange) {
            
            // respond to the request
            exchange.respond("Hello World!");
        }
    }

//    @Test
//    public void testObservationsRemote() {
//        String responseMsg = target.path("observations/coap.me").request().get(String.class);
//        System.out.println(responseMsg);
//    }
//
//    @Test
//    public void testObservationsLocal() {
//        String responseMsg = target.path("observations/localhost").request().get(String.class);
//        System.out.println(responseMsg);
//    }
}

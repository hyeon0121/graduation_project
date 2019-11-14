package com.example.demo.resource;

import com.example.demo.global.CoAPGlobal;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.net.SocketException;
import java.util.Random;

public class PutResource extends CoapResource {
    @Value("${my.ip}") private String myip;

    public PutResource(String name) {
        super(name);
    }

    @Override
    public void handlePUT(CoapExchange exchange) {

        try {
            CoAPGlobal.countMethod++;

            if (CoAPGlobal.nextMethod != "PUT") {
                CoAPGlobal.methodScore -= 20;
            }

            int port = CoAPGlobal.port;

            String url = "coap://" + myip +":"+port;
//            String url = CoAPGlobal.setUrl();
            Random generator = new Random();

            JSONObject parsedObject = new JSONObject(exchange.getRequestText());

            // From Client, JSONObject key "VALUE" Parsing & save local variable value
            int value = parsedObject.getInt("VALUE");

            if (value == CoAPGlobal.putValue) {
                System.out.println("PUT TEST");
                System.out.println("VALUE : " + value);
                System.out.println("====================");

                CoAPGlobal.putScore = 20;
                CoAPGlobal.putValue = 0;
            } else {
                CoAPGlobal.putScore = 0;
                CoAPGlobal.putValue = 0;
            }

            JSONObject json = new JSONObject();
            int index = -1;

            for (int i = 0; i < CoAPGlobal.check.length; i++) {
                if (CoAPGlobal.check[i] != 1) {
                    index = i;
                }
            }

            if (index == -1) {
                System.out.println("CoAP Unit Test Completed\n");

                json.put("NEXT_METHOD", null);
                for (int i = 0; i < CoAPGlobal.check.length; i++) {
                    CoAPGlobal.check[i] = 0;
                }
            } else if (index == 1) {
                CoAPGlobal.nextMethod = CoAPGlobal.methods[index];
                CoAPGlobal.check[index] = 1;
                CoAPGlobal.postValue = generator.nextInt(100) + 1;

                json.put("NEXT_METHOD", CoAPGlobal.methods[index]);
                json.put("URL", url+"/post");
                json.put("VALUE", CoAPGlobal.postValue);
            } else if (index == 2) {
                CoAPGlobal.nextMethod = CoAPGlobal.methods[index];
                CoAPGlobal.check[index] = 1;
                json.put("NEXT_METHOD", CoAPGlobal.methods[index]);
                json.put("URL", url+"/delete");
            }

            String payload = json.toString();

            exchange.respond(CoAP.ResponseCode.CONTENT, payload, MediaTypeRegistry.APPLICATION_JSON);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

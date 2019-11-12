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

public class DeleteResource extends CoapResource {
    @Value("${my.ip}") private String myip;

    public DeleteResource(String name) {
        super(name);
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        try {
            CoAPGlobal.countMethod++;

            if (CoAPGlobal.nextMethod != "DELETE") {
                CoAPGlobal.methodScore -= 20;
            }

            System.out.println("DELETE TEST");
            System.out.println("====================");

            int port = CoAPGlobal.port;

            String url = CoAPGlobal.setUrl();

            Random generator = new Random();

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


            } else if (index == 0) {
                CoAPGlobal.nextMethod = CoAPGlobal.methods[index];
                CoAPGlobal.check[index] = 1;
                CoAPGlobal.putValue = generator.nextInt(100) + 1;

                json.put("NEXT_METHOD", CoAPGlobal.methods[index]);
                json.put("URL", url+"/put");
                json.put("VALUE", CoAPGlobal.putValue);
            } else if (index == 1) {
                CoAPGlobal.nextMethod = CoAPGlobal.methods[index];
                CoAPGlobal.check[index] = 1;
                CoAPGlobal.postValue = generator.nextInt(100) + 1;

                json.put("NEXT_METHOD", CoAPGlobal.methods[index]);
                json.put("URL", url+"/post");
                json.put("VALUE", CoAPGlobal.postValue);
            }

            String payload = json.toString();
            exchange.getRequestCode();

            exchange.respond(CoAP.ResponseCode.CONTENT, payload, MediaTypeRegistry.APPLICATION_JSON);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}

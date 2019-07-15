package com.example.demo.resource;

import com.example.demo.global.CoAPGlobal;
import jdk.nashorn.internal.objects.Global;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;

import java.util.Random;

public class GetResource extends CoapResource {

    public GetResource(String name) {
        super(name);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            CoAPGlobal.countMethod++;
            System.out.println("GET TEST");
            System.out.println("====================");

            String url = CoAPGlobal.setUrl();
            JSONObject json = new JSONObject();

            Random generator = new Random();

            int index = generator.nextInt(2);

            CoAPGlobal.check[index] = 1;

            CoAPGlobal.nextMethod = CoAPGlobal.methods[index];

            if (index == 0) {
                CoAPGlobal.putValue = generator.nextInt(100) + 1;

                json.put("NEXT_METHOD", CoAPGlobal.methods[index]);
                json.put("URL", url+"/put");
                json.put("VALUE", CoAPGlobal.putValue);
            } else if (index == 1) {
                CoAPGlobal.postValue = generator.nextInt(100) + 1;

                json.put("NEXT_METHOD", CoAPGlobal.methods[index]);
                json.put("URL", url+"/post");
                json.put("VALUE", CoAPGlobal.postValue);

            } else {
                json.put("NEXT_METHOD", CoAPGlobal.methods[index]);
                json.put("URL", url+"/delete");
            }

            String payload = json.toString();

            // exchange.accept();

            exchange.respond(CoAP.ResponseCode.CONTENT, payload, MediaTypeRegistry.APPLICATION_JSON);

        } catch (Exception e) {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST, "Wrong Access");
        }

    }
}

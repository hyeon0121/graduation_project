package com.example.demo.resource;

import com.example.demo.global.CoAPGlobal;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.net.SocketException;
import java.util.Random;

public class PostResource extends CoapResource {
	@Value("${my.ip}") private String myip;

	public PostResource(String name) {
		super(name);
	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		
		try {
			CoAPGlobal.countMethod++;

			if (CoAPGlobal.nextMethod != "POST") {
				CoAPGlobal.methodScore -= 20;
			}


			int port = CoAPGlobal.port;

//            String url = CoAPGlobal.setUrl();

			String url = "coap://" + myip +":"+port;

			Random generator = new Random();

			JSONObject parsedObject = new JSONObject(exchange.getRequestText());
			

			// From Client, JSONObject key "VALUE" Parsing & save local variable value
			int value = parsedObject.getInt("VALUE");
			
			if (value == CoAPGlobal.postValue) {
				System.out.println("POST TEST");
				System.out.println("VALUE : " + value);
				System.out.println("====================");

				CoAPGlobal.postScore = 20;
				CoAPGlobal.postValue = 0;
			} else {
				CoAPGlobal.postScore = 0;
				CoAPGlobal.postValue = 0;
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
			} else if (index == 0) {
				CoAPGlobal.nextMethod = CoAPGlobal.methods[index];
				CoAPGlobal.check[index] = 1;
				CoAPGlobal.putValue = generator.nextInt(100) + 1;

				json.put("NEXT_METHOD", CoAPGlobal.methods[index]);
				json.put("URL", url+"/put");
				json.put("VALUE", CoAPGlobal.putValue);
			} else if (index == 2) {
				CoAPGlobal.nextMethod = CoAPGlobal.methods[index];
				CoAPGlobal.check[index] = 1;

				json.put("NEXT_METHOD", CoAPGlobal.methods[index]);
				json.put("URL", url+"/delete");
			}

			String payload = json.toString();

			exchange.respond(ResponseCode.CONTENT, payload , MediaTypeRegistry.APPLICATION_JSON);
			
		} catch (JSONException e) {
			exchange.respond(ResponseCode.BAD_REQUEST, "Wrong Access");
		}

    }
}

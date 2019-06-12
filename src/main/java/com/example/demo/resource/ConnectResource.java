package com.example.demo.resource;

import com.example.demo.global.DeviceInfo;
import com.example.demo.global.Global;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONException;
import org.json.JSONObject;


//observer

import java.util.Random;

public class ConnectResource extends CoapResource {
	
	public ConnectResource(String name) {
		super(name);
	}

	@Override
	public void handlePOST(CoapExchange exchange) {
		
		try {
			
			String id, state, mode;
			JSONObject parsedObject = new JSONObject(exchange.getRequestText().toString());

			// From Client, JSONObject key "DeviceID" Parsing & save local variable id 
			id = parsedObject.getString("DeviceID");
			// From Client, JSONObject key "State" Parsing & save local variable state
			state = parsedObject.getString("State");
			// From Client, JSONObject key "Mode" Parsing & save local variable mode
			mode = parsedObject.getString("Mode");
			
			
			System.out.println("CONNECT DEVICE");
			System.out.println("=========");
			System.out.println("DEVICE ID:" + id);
			System.out.println("DEVICE State:" + state);
			System.out.println("DEVICE Mode:" + mode);
			System.out.println("=========");
			
			// Make a Response value with JSONObject
			JSONObject json = new JSONObject();
			Random generator = new Random();

			Global.message = generator.nextInt(100) + 1;


			json.put("Response", Global.message);

			String payload = json.toString();
			
			// Response Values to Client
			exchange.respond(ResponseCode.CONTENT, payload , MediaTypeRegistry.APPLICATION_JSON);


			// Requested value Save into Data Structure
			DeviceInfo dev_info = new DeviceInfo(id, state, mode);
			Global.device_list.put(id, dev_info);


		    if(mode.equals("push")) {
		    	ObserveResource obs_resource = new ObserveResource(id);
		    	Global.getObserve_resource().add(obs_resource);
		    	dev_info.setResource(obs_resource);
		    }
		    
			
		} catch (JSONException e) {
			exchange.respond(ResponseCode.BAD_REQUEST, "Wrong Access");
		}
	

	}
}

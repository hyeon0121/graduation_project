package com.example.demo.resource;

import com.example.demo.global.DeviceInfo;
import com.example.demo.global.Global;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONException;
import org.json.JSONObject;

public class ReportResource extends CoapResource {
	
	
	/*
	 * Start Report Resource
	 * 
	 * 1. Apply "report"resource through Constructor 
	 * 2. PUT Handler
	 * 
	 */ 
	
	// 1. Apply "report"resource through Constructor 
	public ReportResource(String name) {
		super(name);
	}
	
	

	
	
	
	@Override
	public void handlePUT(CoapExchange exchange) {
		/*
		 * 2. PUT Handler
		 *  2-1. Search DeviceID through Client's URL. 
		 *  2-2. Search DeviceID value from Data Structure
		 * 	2-3. JSON Parsing requested value "State" from Device(Client)
		 *  2-4. Make a Response value with JSONObject
		 *  2-5. Response Values to Client
		 * 
		 */
		
			try {
				
				//2-1. Search DeviceID through Client's URL.
				//* Fill () in here
				//get Last URI, ex)DEVICE1 
				String deviceID = getName();
				//2-2. Search DeviceID value from Data Structure
				DeviceInfo device = Global.device_list.get(deviceID);
				//2-3. JSON Parsing requested value "State" from Device(Client)
				//* Fill () in here
				JSONObject parse = new JSONObject(exchange.getRequestText().toString());
				String state = parse.getString("State");
			
				
				//2-4. Make a Response value with JSONObject
				// If Device is NULL, "failure" Value
				// else, "success" Value
				//* Fill () in here
				JSONObject json = new JSONObject();
				String payload;
				if (device == null) {
					json.put("Response", "failure");
				} else {
					device.setState(state);
					json.put("Response", "success");
				}
				payload = json.toString();
				
				//2-5. Response Values to Client
				// Fill () in here
				exchange.respond(ResponseCode.CONTENT, payload, MediaTypeRegistry.APPLICATION_JSON);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	}
}

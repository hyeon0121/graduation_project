package com.example.demo.resource;

import com.example.demo.global.DeviceInfo;
import com.example.demo.global.Global;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;

public class ObserveResource extends CoapResource {

	/*
	 * Start Observe Resource
	 * 
	 * 1. Apply "observe"resource through Constructor 2. Get Handler
	 * 
	 */

	/*
	 * 1. Apply "observe"resource through Constructor 1-1. ObserverSetting 1-2.
	 * Observe Notification type
	 */
	public ObserveResource(String name) {
		super(name);
		//fill () in here
		// enable observing
		
		setObservable(true);
		
		// configure the notification type to CONs
		setObserveType(Type.NON);
		getAttributes().setObservable(); // mark observable in the Link-Format
	}

	/*
	 * 2. Get Handler 2-1. Search DeviceID through Client's URL. 2-2. Search
	 * DeviceID value from Data Structure 2-3. Make a Response value with JSONObject
	 * & Get Event 2-4. Response Values to Client
	 */
	@Override
	public void handleGET(CoapExchange exchange) {

		try {
			String id = getName();
			DeviceInfo device = Global.device_list.get(id);
			
			//fill () in here
			//From Main, get Device Event 
			String event = device.getEvent();

			JSONObject json = new JSONObject();
			json.put("Control", event);
			String payload = json.toString();
			exchange.respond(ResponseCode.CONTENT, payload, MediaTypeRegistry.APPLICATION_JSON);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
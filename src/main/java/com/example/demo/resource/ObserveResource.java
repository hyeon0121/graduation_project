package com.example.demo.resource;

import com.example.demo.global.DeviceInfo;
import com.example.demo.global.CoAPGlobal;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;

public class ObserveResource extends CoapResource {

	public ObserveResource(String name) {
		super(name);

		// enable observing
		setObservable(true);
		
		// configure the notification type to NONs
		setObserveType(Type.NON);
		getAttributes().setObservable(); // mark observable in the Link-Format
	}

	@Override
	public void handleGET(CoapExchange exchange) {

		try {
			String id = getName();
			DeviceInfo device = CoAPGlobal.device_list.get(id);

			//From Main, get Device Event 
			int event = device.getTemperatures();

			JSONObject json = new JSONObject();
			json.put("Temperature", event);
			String payload = json.toString();
			exchange.respond(ResponseCode.CONTENT, payload, MediaTypeRegistry.APPLICATION_JSON);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
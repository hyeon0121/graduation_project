package com.example.demo.coapserver;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;

public class CoAPServerBase {

	public CoAPServerBase() {

		/*
		 * Run the CoAP Server
		 * 
		 * 1. Create CoAP Server Instance
		 * 2. Add Resources 
		 * 	2-1. Connect Resource
		 * 	2-2. Observe Resource
		 * 3. Run CoAP Server
		 */
		
		CoapServer server = new CoapServer();

		// 2. Add Resources
		// 2-1. Connect Resource
		// Add ConnectResource("connect") into server instance
        //() Fill in here 
		
		server.add(new ConnectResource("connect"));

		//2-4. Observe
		CoapResource observe_res = new CoapResource("obs");
		Global.setObserve_resource(observe_res);
		server.add(observe_res);

		// 3. Run CoAP Server
		server.start();

	}

	

}
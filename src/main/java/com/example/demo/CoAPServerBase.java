package com.example.demo;

import com.example.demo.global.Global;
import com.example.demo.resource.ConnectResource;
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
		 * 	2-2. Control Resource 
		 * 	2-3. Report Resource 
		 * 3. Run CoAP Server
		 */
		
		CoapServer server = new CoapServer();

		// 2. Add Resources
		// 2-1. Connect Resource
		// Add ConnectResource("connect") into server instance
        //() Fill in here 
		
		server.add(new ConnectResource("connect"));

		// 2-2. Control Resource
		CoapResource control_res = new CoapResource("control");
		// Set Resource as global variable
		Global.setControl_resource(control_res);
		server.add(control_res);

		// 2-3. Report Resource
		CoapResource report_res = new CoapResource("report");
		// Set Resource as global variable
		Global.setReport_resource(report_res);
		server.add(report_res);

		//2-4. Observe
		CoapResource observe_res=new CoapResource("obs");
		Global.setObserve_resource(observe_res);
		server.add(observe_res);
		

		// 3. Run CoAP Server
		server.start();

	}

	

}
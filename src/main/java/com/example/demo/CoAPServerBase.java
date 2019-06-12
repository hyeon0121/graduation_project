package com.example.demo;

import com.example.demo.resource.ConnectResource;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import com.example.demo.global.Global;

public class CoAPServerBase {

	public CoAPServerBase() {
		
		CoapServer server = new CoapServer();
		
		server.add(new ConnectResource("connect"));

		CoapResource observe_res = new CoapResource("obs");
		Global.setObserve_resource(observe_res);
		server.add(observe_res);

		server.start();

	}

	

}
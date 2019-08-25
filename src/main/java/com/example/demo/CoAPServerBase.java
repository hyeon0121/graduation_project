package com.example.demo;

import com.example.demo.resource.*;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import com.example.demo.global.CoAPGlobal;

public class CoAPServerBase {

	public CoAPServerBase() {
		
		CoapServer server = new CoapServer();
		
		server.add(new ConnectResource("connect"));

		CoapResource observe_res = new CoapResource("obs");
		CoAPGlobal.setObserve_resource(observe_res);
		server.add(observe_res);

		server.add(new GetResource("get"));
		server.add(new PutResource("put"));
		server.add(new PostResource("post"));
		server.add(new DeleteResource("delete"));

		server.start();

	}

}
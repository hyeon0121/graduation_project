package com.example.demo.server;

import com.sun.net.httpserver.HttpServer;
//import org.apache.http.impl.bootstrap.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

@SuppressWarnings("restriction")
public class SubWebServer {

	private static int port;

	private HttpServer server;

	String getAnswer;
	@SuppressWarnings("static-access")

	public SubWebServer(int port, String getAnswer) {
		this.port = port;
		this.getAnswer = getAnswer;
	}

	@SuppressWarnings("static-access")

	public void start() {
		try {
			server = HttpServer.create(new InetSocketAddress(this.port), 0);

			server.createContext("/scenarioTest", new SubWebServerHandler.scenarioHandler(getAnswer));
			server.createContext("/getHandleTest", new SubWebServerHandler.getHandler(getAnswer));
			server.createContext("/postHandleTest", new SubWebServerHandler.postHandler());

			server.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void stop(int args) {

		Thread aa = new Thread();
		try {
			aa.sleep(args);
			server.stop(args);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
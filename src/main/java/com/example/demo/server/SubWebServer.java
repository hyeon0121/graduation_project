package com.example.demo.server;

import com.example.demo.model.Student;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

@SuppressWarnings("restriction")
public class SubWebServer {

	private static int port;

	private HttpServer server;

	String getAnswer;
	Student student;
	@SuppressWarnings("static-access")

	public SubWebServer(int port, String getAnswer, Student student) {
		this.port = port;
		this.getAnswer = getAnswer;
		this.student = student;
	}

	public SubWebServer(int port, Student student) {
		this.port = port;
        this.student = student;
    }

	@SuppressWarnings("static-access")

	public void start() {
		try {
			server = HttpServer.create(new InetSocketAddress(this.port), 0);

			server.createContext("/scenarioGetTest", new SubWebServerHandler.scenarioGetHandler(getAnswer, student));
			server.createContext("/scenarioPostTest", new SubWebServerHandler.scenarioPostHandler(student));
			server.createContext("/unitHandleTest", new SubWebServerHandler.unitHandler(student));

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
package com.example.demo.global;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;

import org.eclipse.californium.core.CoapResource;


public class CoAPGlobal {
	// CoAP Client Scenario Test
	public static CoapResource temperature_resource;

    public static int temperatures[];
    public static int port = 5683;
	public static int message; // connect test answer
	public static int max; // observe test answer
	public static String deviceId = "1";

	public static int[] getTemperatures() {
        return temperatures;
    }

    public static void setTemperatures(int[] temperatures) {
        CoAPGlobal.temperatures = temperatures;
    }

	public static CoapResource observe_resource;
	public static CoapResource getObserve_resource() {
		return observe_resource;
	}

	public static void setObserve_resource(CoapResource observe_resource) {
		CoAPGlobal.observe_resource = observe_resource;
	}

	public static HashMap<String, DeviceInfo> device_list = new HashMap<String, DeviceInfo>();


	public static CoapResource getTemperature_resource() {
		return temperature_resource;
	}

	public static void setTemperature_resource(CoapResource temperature_resource) {
		CoAPGlobal.temperature_resource = temperature_resource;
	}

	public static HashMap<String, DeviceInfo> getDevice_list() {
		return device_list;
	}

	public static void setDevice_list(HashMap<String, DeviceInfo> device_list) {
		CoAPGlobal.device_list = device_list;
	}

	// CoAP Client Unit Test
	public static String url;
	public static int countMethod = 0;
	public static String methods[] = {"PUT", "POST", "DELETE"};
	public static int check[] = {0, 0, 0};
	public static int putValue;
	public static int postValue;
	public static int methodScore = 60;
	public static int putScore = 0;
	public static int postScore = 0;
	public static String nextMethod;

	public static String setUrl() throws SocketException {
		int port = CoAPGlobal.port;

		String ip = null;

		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
			NetworkInterface intf = en.nextElement();
			for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
				InetAddress inetAddress = enumIpAddr.nextElement();
				if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
					ip = inetAddress.getHostAddress();
				}
			}
		}

		String url = "coap://" + ip +":"+port;

		return url;
	}

	public static String getUrl() {
		return url;
	}
	
}

package com.example.demo.global;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;


public class Global {
	public static CoapResource temperature_resource;

	// temperature array
    public static int temperatures[];
    public static int port = 5683;
	public static int random;

    public static int[] getTemperatures() {
        return temperatures;
    }

    public static void setTemperatures(int[] temperatures) {
        Global.temperatures = temperatures;
    }

    //* �߰�
	public static CoapResource observe_resource;
	//* Getter Setter �߰�
	public static CoapResource getObserve_resource() {
		return observe_resource;
	}

	public static void setObserve_resource(CoapResource observe_resource) {
		Global.observe_resource = observe_resource;
	}

	// device_info
	public static HashMap<String, DeviceInfo> device_list = new HashMap<String, DeviceInfo>();


	public static CoapResource getTemperature_resource() {
		return temperature_resource;
	}

	public static void setTemperature_resource(CoapResource temperature_resource) {
		Global.temperature_resource = temperature_resource;
	}

	public static HashMap<String, DeviceInfo> getDevice_list() {
		return device_list;
	}

	public static void setDevice_list(HashMap<String, DeviceInfo> device_list) {
		Global.device_list = device_list;
	}
	
}

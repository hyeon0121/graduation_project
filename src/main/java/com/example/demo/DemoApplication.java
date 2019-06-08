package com.example.demo;

import com.example.demo.global.DeviceInfo;
import com.example.demo.global.Global;
import org.apache.catalina.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DemoApplication.class, args);

		/*
		 * 1. Run CoAP Server
		 * 2. Process user Event
		 *
		 */

		// 2. Run CoAP Server
		new CoAPServerBase();

		// 2. Process user Event
		while (true) {

			// 2-1. Get Device ID
			DeviceInfo device = Global.device_list.get("1");
			if (device == null) {
				Thread.sleep(1000);
				continue;
			}

			System.out.println(device.getDeviceID());

			// 2-2. Get Event State
			if (Global.getTemperatures() != null) {
				int i = 0;
				int[] numbers = Global.getTemperatures();

				while (i < 5) {
					int input = numbers[i];

					device.TemperatureEvent(input);
					i++;

					Thread.sleep(1000);
				}

				Thread.sleep(5000);
			}

		}

	}

}

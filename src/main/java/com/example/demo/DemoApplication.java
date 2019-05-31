package com.example.demo;

import com.example.demo.global.DeviceInfo;
import com.example.demo.global.Global;
import org.apache.catalina.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {
	static Scanner scan = new Scanner(System.in);

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
			/*
			 * 3. Process User Event
			 *
			 * 3-1. Get Device ID
			 * 3-2. Get Event State
			 * 3-3. Set Device Event
			 */

			// 2-1. Get Device ID
			DeviceInfo device = Global.device_list.get(1);

			// 2-2. Get Event State
			String state = null;
			int i = 0;
			int[] numbers = Global.getTemperatures();

			while (i < 10) {
				int input = numbers[i];
				state = String.valueOf(input);

				Thread.sleep(1000);

				i++;
			}

			// 2-3. Set Device Event
			//device.ControlEvent(state);
		}

	}

}

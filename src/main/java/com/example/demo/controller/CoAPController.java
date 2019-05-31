package com.example.demo.controller;

import com.example.demo.global.Global;
import com.example.demo.model.Student;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@CrossOrigin
@RestController
public class CoAPController {
    private static Map<Integer, Student> studentRepo = new HashMap<>();

    @RequestMapping(value = "/url", method = RequestMethod.GET)
    @ResponseBody
    public  ResponseEntity<Object> getconnectURL() throws JSONException {
        //studentRepo.put(1, student);
        int port = Global.port;

        InetAddress ip;
        String url = null;


        try {
            ip = InetAddress.getLocalHost();
            url = "coap://" + ip.getHostAddress()+":"+port+"/connect";

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Random generator = new Random();

        int random = generator.nextInt(100) + 1;

        JSONObject json = new JSONObject();
        json.put("url", url);
        json.put("message", random);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/observer", method = RequestMethod.GET)
    @ResponseBody
    public  ResponseEntity<Object> getobsURL() throws JSONException {
        int port = Global.port;

        InetAddress ip;
        String url = null;

        try {
            ip = InetAddress.getLocalHost();
            url = "coap://" + ip.getHostAddress()+":"+port+"/obs/1";

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Random generator = new Random();

        int temps[] = new int[10];
        int max = 0;

        for (int i = 0; i < 10; i++) {
            temps[i] = generator.nextInt(20) + 20;
            if (temps[i] > max) {
                max = temps[i];
            }
        }

        Global.setTemperatures(temps);

        JSONObject json = new JSONObject();
        json.put("url", url);
        json.put("max", max);

        return ResponseEntity.ok()
                .body(json.toString());
    }

}

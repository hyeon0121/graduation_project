package com.example.demo.controller;

import com.example.demo.global.Global;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/conn", method = RequestMethod.GET)
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

        JSONObject json = new JSONObject();
        json.put("url", url);
        json.put("message", Global.random);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/obs", method = RequestMethod.GET)
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

        int temps[] = new int[5];
        int max = 0;

        for (int i = 0; i < 5; i++) {
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

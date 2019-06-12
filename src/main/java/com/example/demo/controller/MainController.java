package com.example.demo.controller;

import com.example.demo.global.Global;
import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Random;

@CrossOrigin
@RestController
public class CoAPController {

    @Autowired
    private StudentService studentMapper;

    @RequestMapping(value = "/conn", method = RequestMethod.GET)
    @ResponseBody
    public  ResponseEntity<Object> getConnectURL() throws JSONException, SocketException {
        //studentRepo.put(1, student);
        int port = Global.port;

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

        String url = "coap://" + ip +":"+port+"/connect";
        JSONObject json = new JSONObject();
        json.put("url", url);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/obs", method = RequestMethod.GET)
    @ResponseBody
    public  ResponseEntity<Object> getObsURL() throws JSONException, SocketException {
        int port = Global.port;

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

        String url = "coap://" + ip +":"+port+"/obs/" + Global.deviceId;

        Random generator = new Random();

        int temps[] = new int[5];
        int max = 0;

        for (int i = 0; i < 5; i++) {
            temps[i] = generator.nextInt(20) + 20;
            if (temps[i] > max) {
                max = temps[i];
                Global.max = max;
            }
        }

        Global.setTemperatures(temps);

        System.out.println(url);

        JSONObject json = new JSONObject();
        json.put("url", url);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public  ResponseEntity<Object> getResult() throws JSONException {

        JSONObject json = new JSONObject();
        json.put("message", Global.message);
        json.put("max", Global.max);

        System.out.println(Global.message);
        System.out.println(Global.max);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/score", method = RequestMethod.POST)
    @ResponseBody
    public  ResponseEntity<String> insertTestResult(@RequestBody Student student) throws Exception {

        System.out.println(student.toString());

        studentMapper.insertStudent(student);
        System.out.println(LocalDateTime.now());

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}

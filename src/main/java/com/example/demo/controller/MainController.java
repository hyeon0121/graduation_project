package com.example.demo.controller;

import com.example.demo.global.CoAPGlobal;
import com.example.demo.model.History;
import com.example.demo.global.HttpGlobal;
import com.example.demo.model.Student;
import com.example.demo.server.StaticHandler;
import com.example.demo.server.SubWebServer;
import com.example.demo.service.HistoryService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Random;

@CrossOrigin
@RestController
public class MainController {

    @Autowired
    private HistoryService historyService;

    // WebClient test
    @RequestMapping(value = "/http_get", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> httpGetUrl() throws JSONException {

        int port = StaticHandler.findFreePort();
        HttpGlobal.port = port;

        InetAddress ip;
        String url = null;

        Random random = new Random();

        int pick = random.nextInt(HttpGlobal.proverbArr.length); // 0 ~ 4

        String getAnswer = HttpGlobal.proverbArr[pick];

        HttpGlobal.getAnswer = getAnswer;
        System.out.println(getAnswer);

        try {
            ip = InetAddress.getLocalHost();
            url = "http://" + ip.getHostAddress()+":"+port+"/getHandleTest";

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject();
        json.put("url", url);

        new Thread(new Runnable() {

            public void run() {
                SubWebServer testServer = new SubWebServer(port, getAnswer);
                testServer.start();
                testServer.stop(1000000);
            }
        }).start();

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/http_post", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> httpPostUrl() throws JSONException {

        int port = HttpGlobal.port;

        InetAddress ip;
        String url = null;

        try {
            ip = InetAddress.getLocalHost();
            url = "http://" + ip.getHostAddress()+":"+port+"/postHandleTest";

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject();
        json.put("url", url);

        return ResponseEntity.ok()
                .body(json.toString());
    }


    @RequestMapping(value = "/http_result", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getHttpResult() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("getAnswer", HttpGlobal.getAnswer);
        json.put("postAnswer", HttpGlobal.postAnswer);

        System.out.println("GET ANSWER : " + HttpGlobal.getAnswer);
        System.out.println("POST ANSWER : " + HttpGlobal.postAnswer);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/http_submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getHttpSubmit(@RequestBody Student student) throws JSONException {
        System.out.println(student.toString());

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // CoAP Unit Test
    @RequestMapping(value = "/unit_test", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> unitTestURL() throws JSONException, SocketException {
        CoAPGlobal.methodScore = 60;
        CoAPGlobal.putScore = 0;
        CoAPGlobal.postScore = 0;

        String url = CoAPGlobal.setUrl() + "/get";
        JSONObject json = new JSONObject();
        json.put("url", url);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/unit_score", method = RequestMethod.GET)
    @ResponseBody
    public  ResponseEntity<Object> unitTestScore() throws JSONException {
        JSONObject json = new JSONObject();

        int methodScore = CoAPGlobal.methodScore;

        if (CoAPGlobal.countMethod < 4){
            methodScore = 0;
        }

        json.put("MethodScore", methodScore);
        json.put("PostScore", CoAPGlobal.postScore);
        json.put("PutScore", CoAPGlobal.putScore);

        System.out.println(methodScore);
        System.out.println(CoAPGlobal.postScore);
        System.out.println(CoAPGlobal.putScore);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/unit_result", method = RequestMethod.POST)
    @ResponseBody
    public  ResponseEntity<String> insertUnitTestResult(@RequestBody History history) throws Exception {

        System.out.println(history.toString());

        //historyService.insertHistory(history);
        System.out.println(LocalDateTime.now());

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // CoAP Scenario Test
    @RequestMapping(value = "/conn", method = RequestMethod.GET)
    @ResponseBody
    public  ResponseEntity<Object> getConnectURL() throws JSONException, SocketException {


        String url = CoAPGlobal.setUrl() + "/connect";
        JSONObject json = new JSONObject();
        json.put("url", url);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/obs", method = RequestMethod.GET)
    @ResponseBody
    public  ResponseEntity<Object> getObsURL() throws JSONException, SocketException {

        String url = CoAPGlobal.setUrl() +"/obs/" + CoAPGlobal.deviceId;

        Random generator = new Random();

        int temps[] = new int[5];
        int max = 0;

        for (int i = 0; i < 5; i++) {
            temps[i] = generator.nextInt(20) + 20;
            if (temps[i] > max) {
                max = temps[i];
                CoAPGlobal.max = max;
            }
        }

        CoAPGlobal.setTemperatures(temps);

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
        json.put("message", CoAPGlobal.message);
        json.put("max", CoAPGlobal.max);

        System.out.println(CoAPGlobal.message);
        System.out.println(CoAPGlobal.max);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/score", method = RequestMethod.POST)
    @ResponseBody
    public  ResponseEntity<String> insertTestResult(@RequestBody History history) throws Exception {

        System.out.println(history.toString());

        //historyService.insertHistory(history);
        System.out.println(LocalDateTime.now());

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}

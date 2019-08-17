package com.example.demo.controller;

import com.example.demo.client.CliCnt;
import com.example.demo.global.CoAPGlobal;
import com.example.demo.model.*;
import com.example.demo.global.HttpGlobal;
import com.example.demo.server.StaticHandler;
import com.example.demo.server.SubWebServer;
import com.example.demo.service.HistoryService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Random;

@CrossOrigin
@RestController
public class MainController {
    static int cnt = 0;

    @Autowired
    private HistoryService historyService;

    // Web Server test
    @RequestMapping(value = "/web_server", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> webServer(@RequestBody Student student) throws JSONException, IOException {

        System.out.println("Served by /request handler...");

        System.out.println(student.toString());

        CliCnt cc = new CliCnt(student);
        cc.setName("Client Thread"+(cnt++));
        cc.start();

        System.out.println(cc.getName());

        return ResponseEntity.ok()
                .body("success");
    }

    @RequestMapping(value = "/webserver_result", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> webServerResult(@RequestBody Student student) throws JSONException, IOException {

        StuServerInfo userInfo = HttpGlobal.stuInfo.get(student.getSno());

        int checkScore = 0;

        if(userInfo.isConnTest()) {
            checkScore += 10;
            System.out.println(1);
        }

        if(userInfo.isMultiThread()) {
            checkScore += 10;
            System.out.println(2);

        }

        if(userInfo.isErrorTest200()) {
            System.out.println(3);

            checkScore += 10;
        }

        if(userInfo.isErrorTest201()) {
            System.out.println(4);

            checkScore += 10;
        }

        if(userInfo.isErrorTest404()) {
            System.out.println(5);

            checkScore += 10;
        }

        if(userInfo.isErrorTest400()) {
            System.out.println(6);

            checkScore += 10;
        }

        if(userInfo.isContentLengthTest()) {
            System.out.println(7);

            checkScore += 10;
        }

        if(userInfo.isContentHtmlTest()) {
            System.out.println(8);

            checkScore += 10;
        }

        if(userInfo.isContentImageTest()) {
            System.out.println(9);
            checkScore += 10;
        }

        String score = String.valueOf(checkScore);

        System.out.println("################# score :" + score);

        return ResponseEntity.ok()
                .body(userInfo.toString());
    }

    // Web Client Scenario test
    @RequestMapping(value = "/http_scenario", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> httpScenarioTest() throws JSONException {

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
            url = "http://" + ip.getHostAddress()+":"+port+"/scenarioTest";
            System.out.println(url);

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

    @RequestMapping(value = "/http_result", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> httpResult() throws JSONException {
        JSONObject json = new JSONObject();

        // ..
//        StuCliScenario stuCliScenario = HttpGlobal.webCliSceStuInfo.get("");
//        json.put("httpCheck", stuCliScenario.isHttpCheck());
//        json.put("httpVersion", stuCliScenario.isHttpVersion());
//        json.put("headerUserAgent", stuCliScenario.isUserAgent());
//        json.put("getAnswer", stuCliScenario.getGetAnswer());
//        json.put("postAnswer", stuCliScenario.getPostAnswer());

        json.put("httpCheck", HttpGlobal.httpCheck);
        json.put("httpVersion", HttpGlobal.httpVersion);
        json.put("headerUserAgent", HttpGlobal.headerUserAgent);
        json.put("getAnswer", HttpGlobal.getAnswer);
        json.put("postAnswer", HttpGlobal.postAnswer);

        System.out.println("http : " + HttpGlobal.httpCheck);
        System.out.println("version : " + HttpGlobal.httpVersion);
        System.out.println("header : " + HttpGlobal.headerUserAgent);
        System.out.println("GET ANSWER : " + HttpGlobal.getAnswer);
        System.out.println("POST ANSWER : " + HttpGlobal.postAnswer);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/http_submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> httpSubmit(@RequestBody Student student, int getScore, int postScore) throws JSONException {
        System.out.println(student.toString());

        StuCliScenario stuCliScenario = HttpGlobal.webCliSceStuInfo.get(student.getSno());
        StuCliScore stuCliScore = new StuCliScore(student.getSip(), student.getSname(), student.getSno(), student.getSport(),
                stuCliScenario.isHttpCheck(), stuCliScenario.isHttpCheck(), stuCliScenario.isHttpVersion() );

        stuCliScore.setGetScore(getScore);
        stuCliScore.setPostScore(postScore);

        System.out.println(stuCliScore.toString());

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // Web Client Unit test
    @RequestMapping(value = "/http_get", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> httpGetTest() throws JSONException {

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
            System.out.println(url);

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
    public ResponseEntity<Object> httpPostTest() throws JSONException {

        int port = StaticHandler.findFreePort();
        HttpGlobal.port = port;

        InetAddress ip;
        String url = null;

        try {
            ip = InetAddress.getLocalHost();
            url = "http://" + ip.getHostAddress()+":"+port+"/postHandleTest";
            System.out.println(url);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject();
        json.put("url", url);

        new Thread(new Runnable() {

            public void run() {
                SubWebServer testServer = new SubWebServer(port);
                testServer.start();
                testServer.stop(1000000);
            }
        }).start();

        return ResponseEntity.ok()
                .body(json.toString());
    }


    @RequestMapping(value = "/get_result", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getResult() throws JSONException {
        JSONObject json = new JSONObject();

//        StuCliGet stuCliGet = HttpGlobal.webCliGetStuInfo.get("");
//        json.put("httpCheck", stuCliGet.isHttpCheck());
//        json.put("httpVersion", stuCliGet.isHttpVersion());
//        json.put("headerUserAgent", stuCliGet.isUserAgent());
//        json.put("getAnswer", stuCliGet.getGetAnswer());

        json.put("httpCheck", HttpGlobal.httpCheck);
        json.put("httpVersion", HttpGlobal.httpVersion);
        json.put("headerUserAgent", HttpGlobal.headerUserAgent);
        json.put("getAnswer", HttpGlobal.getAnswer);

        System.out.println("http : " + HttpGlobal.httpCheck);
        System.out.println("version : " + HttpGlobal.httpVersion);
        System.out.println("header : " + HttpGlobal.headerUserAgent);
        System.out.println("GET ANSWER : " + HttpGlobal.getAnswer);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/post_result", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> postResult() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("httpCheck", HttpGlobal.httpCheck);
        json.put("httpVersion", HttpGlobal.httpVersion);
        json.put("headerUserAgent", HttpGlobal.headerUserAgent);
        json.put("postAnswer", HttpGlobal.postAnswer);


        System.out.println("http : " + HttpGlobal.httpCheck);
        System.out.println("version : " + HttpGlobal.httpVersion);
        System.out.println("header : " + HttpGlobal.headerUserAgent);
        System.out.println("POST ANSWER : " + HttpGlobal.postAnswer);

        return ResponseEntity.ok()
                .body(json.toString());
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
    public  ResponseEntity<Object> result() throws JSONException {

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

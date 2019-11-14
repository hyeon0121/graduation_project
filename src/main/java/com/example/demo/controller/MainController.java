package com.example.demo.controller;

import com.example.demo.client.HeaderCliCnt;
import com.example.demo.client.StatusCliCnt;
import com.example.demo.client.ScenarioCliCnt;
import com.example.demo.global.CoAPGlobal;
import com.example.demo.model.*;
import com.example.demo.global.HttpGlobal;
import com.example.demo.server.StaticHandler;
import com.example.demo.server.SubWebServer;
import com.example.demo.service.HistoryService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin
@RestController
public class MainController {

    static int scencnt = 0;
    static int statuscnt = 0;
    static int headercnt = 0;

    @Value("${my.ip}") private String myip;

    Calendar calendar = Calendar.getInstance();

    @Autowired
    private HistoryService historyService;

    // 웹 서버 테스트
    @RequestMapping(value = "/web_server", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> webServerTest(@RequestBody Student student) throws Exception {
        System.out.println("Served by /request handler...");

        System.out.println(student.toString());

        ScenarioCliCnt cc = new ScenarioCliCnt(student);
        cc.setName("[Scenario] Client Thread"+(scencnt++));
        System.out.println(cc.getName());

        // time check
        HttpGlobal.sceStartTime.put(student.getSno(), System.currentTimeMillis());

        cc.start();

        while(true) {
            StuServerInfo userInfo = HttpGlobal.stuInfo.get(student.getSno());

            if (userInfo == null) {
                continue;

            } else {
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

                if(userInfo.isErrorTest404()) {
                    System.out.println(4);

                    checkScore += 10;
                }

                if(userInfo.isErrorTest400()) {
                    System.out.println(5);

                    checkScore += 10;
                }

                if(userInfo.isContentLengthTest()) {
                    System.out.println(6);

                    checkScore += 10;
                }

                if(userInfo.isContentHtmlTest()) {
                    System.out.println(7);

                    checkScore += 10;
                }

                if(userInfo.isContentImageTest()) {
                    System.out.println(8);
                    checkScore += 10;
                }

                if(userInfo.isCookieTest()) {
                    System.out.println(9);
                    checkScore += 10;
                }

                String score = String.valueOf(checkScore);

                System.out.println("################# score :" + score);

                JSONObject json = new JSONObject();
                json.put("connTest", userInfo.isConnTest());
                json.put("multiThread", userInfo.isMultiThread());
                json.put("errorTest200", userInfo.isErrorTest200());
                json.put("errorTest404", userInfo.isErrorTest404());
                json.put("errorTest400", userInfo.isErrorTest400());
                json.put("contentLengthTest", userInfo.isContentLengthTest());
                json.put("contentHtmlTest", userInfo.isContentHtmlTest());
                json.put("contentImageTest", userInfo.isContentImageTest());
                json.put("cookieTest", userInfo.isCookieTest());
                json.put("elapsedTime", userInfo.getElapsedTime());

                // Web Server Scenario Test result insert in db
                historyService.insertWebServerSce(userInfo);

                return ResponseEntity.ok()
                        .body(json.toString());
            }

        }

    }

    // 웹 서버 시나리오 테스트 결과 조회
    @RequestMapping(value = "/web_server/history", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> webServerTestHistory(@RequestBody Student student) throws Exception {

        List<StuServerInfoDAO> testResultList = historyService.selectWebServerSce(student.getSno());

        int size = testResultList.size();
        System.out.println(size);

        JSONArray jArray = new JSONArray();

        try {

            for (int i = 0; i < size; i++) {
                JSONObject sObject = new JSONObject();
                sObject.put("connCorrect", testResultList.get(i).isConnTest());
                sObject.put("multiThreadCorrect", testResultList.get(i).isMultiThread());
                sObject.put("error200Correct", testResultList.get(i).isErrorTest200());
                sObject.put("error404Correct", testResultList.get(i).isErrorTest404());
                sObject.put("error400Correct", testResultList.get(i).isErrorTest400());
                sObject.put("lengthCorrect", testResultList.get(i).isContentLengthTest());
                sObject.put("htmlCorrect", testResultList.get(i).isContentHtmlTest());
                sObject.put("imageCorrect", testResultList.get(i).isContentImageTest());
                sObject.put("cookieCorrect", testResultList.get(i).isCookieTest());
                sObject.put("date", testResultList.get(i).getDate());
                sObject.put("elapsedTime", testResultList.get(i).getElapsedTime());

                jArray.put(sObject.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .body(jArray.toString());
    }


    // Web Server unit test
    @RequestMapping(value = "/server-status", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> webServerStatusTest(@RequestBody Student student) throws JSONException {
        // Time check
        HttpGlobal.statusStartTime.put(student.getSno(), System.currentTimeMillis());

        System.out.println("Served by /request handler...");

        System.out.println(student.toString());

        StatusCliCnt cc = new StatusCliCnt(student);
        cc.setName("[Status Code] Client Thread"+(statuscnt++));

        cc.start();

        System.out.println(cc.getName());

        while(true) {
            StuServerStatus userInfo = HttpGlobal.statusInfo.get(student.getSno());

            if (userInfo == null) {
                continue;

            } else {
                int checkScore = 0;

                if(userInfo.isConnTest()) {
                    System.out.println(1);
                    checkScore += 10;
                }

                if(userInfo.isMultiThread()) {
                    System.out.println(2);
                    checkScore += 10;
                }

                if(userInfo.isErrorTest200()) {
                    System.out.println(3);
                    checkScore += 10;
                }

                if(userInfo.isErrorTest404()) {
                    System.out.println(4);
                    checkScore += 10;
                }

                if(userInfo.isErrorTest400()) {
                    System.out.println(5);
                    checkScore += 10;
                }

                String score = String.valueOf(checkScore);

                System.out.println("################# score :" + score);

                JSONObject json = new JSONObject();
                json.put("connTest", userInfo.isConnTest());
                json.put("multiThread", userInfo.isMultiThread());
                json.put("errorTest200", userInfo.isErrorTest200());
                json.put("errorTest404", userInfo.isErrorTest404());
                json.put("errorTest400", userInfo.isErrorTest400());
                json.put("elapsedTime", userInfo.getElapsedTime());

                return ResponseEntity.ok()
                        .body(json.toString());
            }

        }

    }

    @RequestMapping(value = "/server-header", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> webServerHeaderTest(@RequestBody Student student) throws JSONException {

        System.out.println("Served by /request handler...");

        System.out.println(student.toString());

        HeaderCliCnt cc = new HeaderCliCnt(student);
        cc.setName("[Header] Client Thread"+(headercnt++));

        // Time check
        HttpGlobal.headerStartTime.put(student.getSno(), System.currentTimeMillis());

        cc.start();

        System.out.println(cc.getName());

        while(true) {
            StuServerHeader userInfo = HttpGlobal.headerInfo.get(student.getSno());

            if (userInfo == null) {
                continue;

            } else {
                int checkScore = 0;

                if(userInfo.isConnTest()) {
                    checkScore += 10;
                    System.out.println(1);
                }

                if(userInfo.isMultiThread()) {
                    checkScore += 10;
                    System.out.println(2);
                }

                if(userInfo.isContentLengthTest()) {
                    System.out.println(3);

                    checkScore += 10;
                }

                if(userInfo.isContentHtmlTest()) {
                    System.out.println(4);

                    checkScore += 10;
                }

                if(userInfo.isContentImageTest()) {
                    System.out.println(5);
                    checkScore += 10;
                }

                String score = String.valueOf(checkScore);

                System.out.println("################# score :" + score);

                JSONObject json = new JSONObject();
                json.put("connTest", userInfo.isConnTest());
                json.put("multiThread", userInfo.isMultiThread());
                json.put("contentLengthTest", userInfo.isContentLengthTest());
                json.put("contentHtmlTest", userInfo.isContentHtmlTest());
                json.put("contentImageTest", userInfo.isContentImageTest());
                json.put("elapsedTime", userInfo.getElapsedTime());

                return ResponseEntity.ok()
                        .body(json.toString());
            }

        }

    }

    // Web Client Scenario test
    @RequestMapping(value = "/scenario_get", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> cliScenarioGetTest(@RequestBody Student student) throws JSONException {

        int port = StaticHandler.findFreePort();
        HttpGlobal.port = port;

        //String ip = null;
        String url = null;

        Random random = new Random();

        int pick = random.nextInt(HttpGlobal.proverbArr.length); // 0 ~ 4

        String getAnswer = HttpGlobal.proverbArr[pick];

        HttpGlobal.getAnswer = getAnswer;
        System.out.println(getAnswer);

        InetAddress ip;

        try {
            url = "http://" + myip+":"+port+"/scenarioGetTest";
            System.out.println(url);

        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject();
        json.put("url", url);

        new Thread(new Runnable() {

            public void run() {
                SubWebServer testServer = new SubWebServer(port, getAnswer, student);
                testServer.start();
                testServer.stop(1000000);
            }
        }).start();

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/scenario_post", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> cliScenarioPostTest(@RequestBody Student student) throws JSONException {

        int port = StaticHandler.findFreePort();
        HttpGlobal.port = port;

        String url = null;

        InetAddress ip;

        try {
            url = "http://" + myip +":"+port+"/scenarioPostTest";
            System.out.println(url);

        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject();
        json.put("url", url);

        new Thread(new Runnable() {

            public void run() {
                SubWebServer testServer = new SubWebServer(port, student);
                testServer.start();
                testServer.stop(1000000);
            }
        }).start();

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/http_result", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> httpResult(@RequestBody Student student) throws JSONException {
        Calendar calendar = Calendar.getInstance();

        JSONObject json = new JSONObject();

        if (student.getSno() != null) {
            String sno = student.getSno();
            StuCliScenario stuCliScenario = HttpGlobal.webCliSceStuInfo.get(sno);

            if (stuCliScenario == null) {
                stuCliScenario = new StuCliScenario(student.getSname(), student.getSno(), student.getSip(), student.getSport(), false, false, false, calendar.getTime());
                stuCliScenario.setGetAnswer("");
                stuCliScenario.setPostAnswer("");

                if (HttpGlobal.webCliSceStuInfo.get(student.getSno()) != null) {
                    HttpGlobal.webCliSceStuInfo.replace(student.getSno(), stuCliScenario);
                } else {
                    HttpGlobal.webCliSceStuInfo.put(student.getSno(), stuCliScenario);
                }

                json.put("httpCheck", false);
                json.put("httpVersion", false);
                json.put("headerUserAgent", false);
                json.put("getAnswer", "");
                json.put("postAnswer", "");

                System.out.println("null");

                return ResponseEntity.ok()
                        .body(json.toString());

            } else {
                if (HttpGlobal.hasRequestedGet.get(sno) != null && HttpGlobal.hasRequestedPost.get(sno) != null) {
                    boolean getTest = HttpGlobal.hasRequestedGet.get(sno);
                    boolean postTest = HttpGlobal.hasRequestedPost.get(sno);

                    if (getTest && postTest) {
                        json.put("httpCheck", stuCliScenario.isHttpCheck());
                        json.put("httpVersion", stuCliScenario.isHttpVersion());
                        json.put("headerUserAgent", stuCliScenario.isUserAgent());
                        json.put("getAnswer", stuCliScenario.getGetAnswer());
                        json.put("postAnswer", stuCliScenario.getPostAnswer());

                        return ResponseEntity.ok()
                                .body(json.toString());
                    } else {
                        if (getTest) {
                            json.put("httpCheck", stuCliScenario.isHttpCheck());
                            json.put("httpVersion", stuCliScenario.isHttpVersion());
                            json.put("headerUserAgent", stuCliScenario.isUserAgent());
                            json.put("getAnswer", stuCliScenario.getGetAnswer());
                            json.put("postAnswer", "");

                            return ResponseEntity.ok()
                                    .body(json.toString());
                        } else if (postTest) {
                            json.put("httpCheck", false);
                            json.put("httpVersion", false);
                            json.put("headerUserAgent", false);
                            json.put("getAnswer", "");
                            json.put("postAnswer", stuCliScenario.getPostAnswer());

                            return ResponseEntity.ok()
                                    .body(json.toString());
                        } else {
                            json.put("httpCheck", false);
                            json.put("httpVersion", false);
                            json.put("headerUserAgent", false);
                            json.put("getAnswer", "");
                            json.put("postAnswer", "");

                            return ResponseEntity.ok()
                                    .body(json.toString());
                        }
                    }

                } else {
                    json.put("httpCheck", false);
                    json.put("httpVersion", false);
                    json.put("headerUserAgent", false);
                    json.put("getAnswer", "");
                    json.put("postAnswer", "");

                    return ResponseEntity.ok()
                            .body(json.toString());
                }
            }

        } else {
            json.put("httpCheck", false);
            json.put("httpVersion", false);
            json.put("headerUserAgent", false);
            json.put("getAnswer", "");
            json.put("postAnswer", "");

            return ResponseEntity.ok()
                    .body(json.toString());
        }

    }

    @RequestMapping(value = "/http_submit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> httpSubmit(@RequestBody Student student, boolean getScore, boolean postScore) throws Exception {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm");

        if (student.getSno() != null) {
            System.out.println(student.toString());

            StuCliScenario stuCliScenario = HttpGlobal.webCliSceStuInfo.get(student.getSno());

            if (stuCliScenario != null) {
                Date d = stuCliScenario.getAccessTime();
                String date = formatter.format(d);

                StuCliScore stuCliScore = new StuCliScore(student.getSip(), student.getSname(), student.getSno(), student.getSport(),
                        stuCliScenario.isHttpCheck(), stuCliScenario.isHttpCheck(), stuCliScenario.isHttpVersion(), getScore, postScore, date);

                System.out.println(stuCliScore.toString());
                historyService.insertWebClientSce(stuCliScore);

                return new ResponseEntity<>("success", HttpStatus.OK);

            } else {
                Date d = new Date();
                String date = formatter.format(d);

                StuCliScore stuCliScore = new StuCliScore(student.getSip(), student.getSname(), student.getSno(), student.getSport(),
                        false, false, false, false, false, date);

                System.out.println(stuCliScore.toString());
                historyService.insertWebClientSce(stuCliScore);

                return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
            }

        } else {
            System.out.println("student info is null");
            return new ResponseEntity<>("student info is required", HttpStatus.BAD_REQUEST);
        }

    }

    // Web Client unit test
    @RequestMapping(value = "/http_unit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> httpUnitTest(@RequestBody Student student) throws JSONException {

        int port = StaticHandler.findFreePort();
        HttpGlobal.port = port;

        String url = null;

        InetAddress ip;

        try {
            url = "http://" + myip +":"+port+"/unittHandleTest";
            System.out.println(url);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(url);
        JSONObject json = new JSONObject();
        json.put("url", url);

        new Thread(new Runnable() {

            public void run() {
                SubWebServer testServer = new SubWebServer(port, student);
                testServer.start();
                testServer.stop(1000000);
            }
        }).start();

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/get_result", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> getResult(@RequestBody Student student) throws JSONException {
        JSONObject json = new JSONObject();

        StuCliUnit stuCliUnit = HttpGlobal.webCliUnitStuInfo.get(student.getSno());

        if (stuCliUnit != null) {
            json.put("httpCheck", stuCliUnit.isHttpCheck());
            json.put("httpVersion", stuCliUnit.isHttpVersion());
            json.put("headerUserAgent", stuCliUnit.isUserAgent());
            json.put("requestCheck", stuCliUnit.isRequestCheck());
        } else {
            json.put("httpCheck", false);
            json.put("httpVersion", false);
            json.put("headerUserAgent", false);
            json.put("requestCheck", false);
        }

        StuCliUnit temp = new StuCliUnit(student.getSname(), student.getSno(), student.getSip(), student.getSport(), false, false, false, false, false, calendar.getTime());
        HttpGlobal.webCliUnitStuInfo.put(student.getSno(), temp);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/post_result", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> postResult(@RequestBody Student student) throws JSONException {
        JSONObject json = new JSONObject();

        StuCliUnit stuCliUnit = HttpGlobal.webCliUnitStuInfo.get(student.getSno());

        if (stuCliUnit != null) {
            json.put("httpCheck", stuCliUnit.isHttpCheck());
            json.put("httpVersion", stuCliUnit.isHttpVersion());
            json.put("headerUserAgent", stuCliUnit.isUserAgent());
            json.put("requestCheck", stuCliUnit.isRequestCheck());
            json.put("payloadCheck", stuCliUnit.isPayloadCheck());
        } else {
            json.put("httpCheck", false);
            json.put("httpVersion", false);
            json.put("headerUserAgent", false);
            json.put("requestCheck", false);
            json.put("payloadCheck", false);
        }

        StuCliUnit temp = new StuCliUnit(student.getSname(), student.getSno(), student.getSip(), student.getSport(), false, false, false, false, false, calendar.getTime());
        HttpGlobal.webCliUnitStuInfo.put(student.getSno(), temp);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/put_result", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> putResult(@RequestBody Student student) throws JSONException {
        JSONObject json = new JSONObject();

        StuCliUnit stuCliUnit = HttpGlobal.webCliUnitStuInfo.get(student.getSno());

        if (stuCliUnit != null) {
            json.put("httpCheck", stuCliUnit.isHttpCheck());
            json.put("httpVersion", stuCliUnit.isHttpVersion());
            json.put("headerUserAgent", stuCliUnit.isUserAgent());
            json.put("requestCheck", stuCliUnit.isRequestCheck());
            json.put("payloadCheck", stuCliUnit.isPayloadCheck());

        } else {
            json.put("httpCheck", false);
            json.put("httpVersion", false);
            json.put("headerUserAgent", false);
            json.put("requestCheck", false);
            json.put("payloadCheck", false);
        }

        StuCliUnit temp = new StuCliUnit(student.getSname(), student.getSno(), student.getSip(), student.getSport(), false, false, false, false, false, calendar.getTime());
        HttpGlobal.webCliUnitStuInfo.put(student.getSno(), temp);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/delete_result", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> deleteResult(@RequestBody Student student) throws JSONException {
        JSONObject json = new JSONObject();

        StuCliUnit stuCliUnit = HttpGlobal.webCliUnitStuInfo.get(student.getSno());

        if (stuCliUnit != null) {
            json.put("httpCheck", stuCliUnit.isHttpCheck());
            json.put("httpVersion", stuCliUnit.isHttpVersion());
            json.put("headerUserAgent", stuCliUnit.isUserAgent());
            json.put("requestCheck", stuCliUnit.isRequestCheck());

        } else {
            json.put("httpCheck", false);
            json.put("httpVersion", false);
            json.put("headerUserAgent", false);
            json.put("requestCheck", false);
        }

        StuCliUnit temp = new StuCliUnit(student.getSname(), student.getSno(), student.getSip(), student.getSport(), false, false, false, false, false, calendar.getTime());
        HttpGlobal.webCliUnitStuInfo.put(student.getSno(), temp);

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

        int port = CoAPGlobal.port;



//        String url = CoAPGlobal.setUrl() + "/get";
        String url = "coap://" + myip +":"+port + "/get";
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
    public  ResponseEntity<String> insertUnitTestResult(@RequestBody StuCoAPScenario history) {

        System.out.println(history.toString());

        //historyService.insertHistory(history);
        System.out.println(LocalDateTime.now());

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // CoAP Scenario Test
    @RequestMapping(value = "/conn", method = RequestMethod.GET)
    @ResponseBody
    public  ResponseEntity<Object> getConnectURL() throws JSONException, SocketException {
        int port = CoAPGlobal.port;

//        String url = CoAPGlobal.setUrl() + "/connect";
        String url = "coap://" + myip +":"+port + "/connect";
        JSONObject json = new JSONObject();
        json.put("url", url);

        return ResponseEntity.ok()
                .body(json.toString());
    }

    @RequestMapping(value = "/obs", method = RequestMethod.GET)
    @ResponseBody
    public  ResponseEntity<Object> getObsURL() throws JSONException, SocketException {

        int port = CoAPGlobal.port;

//        String url = CoAPGlobal.setUrl()+"/obs/" + CoAPGlobal.deviceId;

        String url = "coap://" + myip +":"+port +"/obs/" + CoAPGlobal.deviceId;

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
    public  ResponseEntity<String> insertTestResult(@RequestBody StuCoAPScenario history) throws Exception {

        System.out.println(history.toString());

        if (history != null) {
            historyService.insertHistory(history);
            System.out.println(LocalDateTime.now());
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
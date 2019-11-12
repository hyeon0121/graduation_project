package com.example.demo.server;

import com.example.demo.global.HttpGlobal;
import com.example.demo.model.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Calendar;

public class SubWebServerHandler implements HttpHandler {
    public SubWebServerHandler() {

    }

    @Override
    public void handle(HttpExchange ex) throws IOException {

    }

    public static class scenarioGetHandler implements HttpHandler {
        String getAnswer;
        Calendar calendar = Calendar.getInstance();
        StuCliScenario stuCliScenario;
        Student student;

        public scenarioGetHandler(String getAnswer, Student student) {
            this.getAnswer = getAnswer;
            this.student = student;
        }
        @Override
        public void handle(HttpExchange ex) throws IOException {
            boolean httpCheck = false;
            boolean httpVersion = false;
            boolean headerUserAgent = false;
            if (ex.getRequestMethod().equals("GET")) {
                if (HttpGlobal.hasRequestedGet.get(student.getSno()) != null) {
                    HttpGlobal.hasRequestedGet.replace(student.getSno(), true);
                } else {
                    HttpGlobal.hasRequestedGet.put(student.getSno(), true);
                }
                String str = ex.getProtocol();
                String http = str.split("/")[0];
                String version = str.split("/")[1];

                if (http.equals("HTTP")) {
                    httpCheck = true;
                } else {
                    httpCheck = false;
                }

                if (version.equals("1.1")) {
                    httpVersion = true;
                } else {
                    httpVersion = false;
                }

                System.err.println("http : "+http);
                System.err.println("ver : "+version);

                System.err.println("Method : "+ex.getRequestMethod());

                Headers cliHeader2 = ex.getRequestHeaders();
                System.err.println("HEADER2 : "+cliHeader2.get("User-Agent"));

                if(cliHeader2.get("User-agent").toString().contains("ComputerNetwork")) {
                    headerUserAgent = true;
                } else {
                    headerUserAgent = false;
                }

                System.err.println(httpCheck);
                System.err.println(httpVersion);
                System.err.println(headerUserAgent);

                // DB
                stuCliScenario = new StuCliScenario(student.getSname(), student.getSno(), student.getSip(), student.getSport(), httpCheck, httpVersion, headerUserAgent, calendar.getTime());
                stuCliScenario.setGetAnswer(getAnswer);

                if (HttpGlobal.webCliSceStuInfo.get(student.getSno()) != null) {
                    HttpGlobal.webCliSceStuInfo.replace(student.getSno(), stuCliScenario);
                } else {
                    HttpGlobal.webCliSceStuInfo.put(student.getSno(), stuCliScenario);
                }

                OutputStream out = ex.getResponseBody();
                String result = getAnswer;
                ex.sendResponseHeaders(200, result.getBytes().length);
                out.write(result.getBytes());
                out.close();

            } else {
                OutputStream out = ex.getResponseBody();
                ex.sendResponseHeaders(400, "The method must be GET".getBytes().length);
                out.write( "The method must be GET".getBytes());
                out.close();

                if (HttpGlobal.hasRequestedGet.get(student.getSno()) != null) {
                    HttpGlobal.hasRequestedGet.replace(student.getSno(), false);
                } else {
                    HttpGlobal.hasRequestedGet.put(student.getSno(), false);
                }

            }

        }
    }

    public static class scenarioPostHandler implements HttpHandler {
        Student student;
        Calendar calendar = Calendar.getInstance();

        public scenarioPostHandler(Student student) {
            this.student = student;
        }

        @Override
        public void handle(HttpExchange ex) throws IOException {
            if (ex.getRequestMethod().equals("POST")) {
                if (HttpGlobal.hasRequestedPost.get(student.getSno()) != null) {
                    HttpGlobal.hasRequestedPost.replace(student.getSno(), true);
                } else {
                    HttpGlobal.hasRequestedPost.put(student.getSno(), true);
                }

                System.err.println("Method : " + ex.getRequestMethod());

                try {
                    InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), "utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String json = br.readLine();

                    Long baseNum =  Long.parseLong(json);
                    Long tes = (long) (Math.random() * (1000));
                    Long testNum = baseNum - tes;

                    System.out.println(json);

                    String answer = testNum.toString();

                    HttpGlobal.postAnswer = answer;


                    StuCliScenario stuCliScenario = HttpGlobal.webCliSceStuInfo.get(student.getSno());

                    if (stuCliScenario != null) {
                        System.out.println(stuCliScenario.toString());
                        stuCliScenario.setPostAnswer(answer);
                    } else {
                        System.out.println(stuCliScenario.toString());
                        stuCliScenario = new StuCliScenario(student.getSname(), student.getSno(), student.getSip(), student.getSport(), answer, calendar.getTime());
                        HttpGlobal.webCliSceStuInfo.put(student.getSno(), stuCliScenario);
                    }

                    OutputStream out = ex.getResponseBody();
                    System.out.println(answer);
                    ex.sendResponseHeaders(200, answer.getBytes().length);
                    out.write(answer.getBytes());
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                OutputStream out = ex.getResponseBody();
                ex.sendResponseHeaders(400, "The method must be POST".getBytes().length);
                out.write( "The method must be POST".getBytes());
                out.close();

                if (HttpGlobal.hasRequestedPost.get(student.getSno()) != null) {
                    HttpGlobal.hasRequestedPost.replace(student.getSno(), false);
                } else {
                    HttpGlobal.hasRequestedPost.put(student.getSno(), false);
                }
            }

        }
    }
    public static class unitHandler implements HttpHandler {
        Calendar calendar = Calendar.getInstance();
        Student student;

        public unitHandler(Student student) {
            this.student = student;
        }
        @Override
        public void handle(HttpExchange ex) throws IOException {
            boolean httpCheck = false;
            boolean httpVersion = false;
            boolean headerUserAgent = false;
            boolean requestCheck = false;
            boolean payloadCheck = false;

            String method = ex.getRequestMethod();
            String str = ex.getProtocol();
            String http = str.split("/")[0];
            String version = str.split("/")[1];

            if (http.equals("HTTP")) {
                httpCheck = true;
            } else {
                httpCheck = false;
            }

            if (version.equals("1.1")) {
                httpVersion = true;
            } else {
                httpVersion = false;
            }

            System.err.println("http : " + http);
            System.err.println("ver : " + version);

            System.err.println("Method : " + ex.getRequestMethod());

            Headers cliHeader2 = ex.getRequestHeaders();
            System.err.println("Header : " + cliHeader2.get("User-Agent"));

            if (cliHeader2.get("User-agent").toString().contains("ComputerNetwork")) {
                headerUserAgent = true;
            } else {
                headerUserAgent = false;

            }

            if (method.equals("GET")) {
                try {
                    OutputStream out = ex.getResponseBody();
                    String response = "Success";
                    ex.sendResponseHeaders(200, response.getBytes().length);
                    out.write(response.getBytes());
                    out.close();

                    requestCheck = true;

                } catch (Exception e) {
                    e.printStackTrace();

                    OutputStream out = ex.getResponseBody();
                    String response = "Failed";
                    ex.sendResponseHeaders(400, response.getBytes().length);
                    out.write(response.getBytes());
                    out.close();

                }

            }

            if (method.equals("POST")) {
                InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String json = br.readLine();

                try {
                    if (json != null) {
                        Long baseNum =  Long.parseLong(json);
                        Long tes = (long) (Math.random() * (1000));
                        Long testNum = baseNum - tes;

                        System.out.println(json);

                        String answer = testNum.toString();

                        System.out.println(json);
                        System.out.println(testNum);

                        HttpGlobal.postAnswer = answer;

                        OutputStream out = ex.getResponseBody();

                        ex.sendResponseHeaders(200, answer.length());

                        out.write(answer.getBytes());
                        out.close();

                        requestCheck = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    OutputStream out = ex.getResponseBody();

                    ex.sendResponseHeaders(400, "No value for studentID".length());

                    out.write("No value for studentID".getBytes());
                    out.close();
                } finally {

                }

            }

            if (method.equals("PUT")) {
                InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String json = br.readLine();

                try {
                    if (json != null) {
                        payloadCheck = true;

                        System.out.println(json);

                        JSONObject tmp = new JSONObject();

                        tmp.put("studentID", json);

                        OutputStream out = ex.getResponseBody();

                        ex.sendResponseHeaders(200, tmp.toString().length());

                        out.write(tmp.toString().getBytes());
                        out.close();

                        requestCheck = true;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    OutputStream out = ex.getResponseBody();

                    ex.sendResponseHeaders(400, "No value for studentID".length());

                    out.write("No value for studentID".getBytes());
                    out.close();
                }

            }

            if (method.equals("DELETE")) {

                try {
                    String response = "Success";
                    OutputStream out = ex.getResponseBody();

                    ex.sendResponseHeaders(200, response.length());

                    out.write(response.getBytes());
                    out.close();

                    requestCheck = true;

                } catch (Exception e) {
                    e.printStackTrace();

                    String response = "Failed";
                    OutputStream out = ex.getResponseBody();

                    ex.sendResponseHeaders(200, response.length());

                    out.write(response.getBytes());
                    out.close();
                }

            }

            System.err.println(httpCheck);
            System.err.println(httpVersion);
            System.err.println(headerUserAgent);
            System.err.println(payloadCheck);

            if (method.equals("GET") || method.equals("DELETE")) {
                StuCliUnit stuCliUnit = new StuCliUnit(student.getSname(), student.getSno(), student.getSip(), student.getSport(), requestCheck, httpCheck, httpVersion, headerUserAgent, calendar.getTime());
                if (HttpGlobal.webCliUnitStuInfo.get(student.getSno()) != null) {
                    HttpGlobal.webCliUnitStuInfo.replace(student.getSno(), stuCliUnit);
                } else {
                    HttpGlobal.webCliUnitStuInfo.put(student.getSno(), stuCliUnit);
                }
            }

            else if (method.equals("POST") || method.equals("PUT")) {
                StuCliUnit stuCliUnit = new StuCliUnit(student.getSname(), student.getSno(), student.getSip(), student.getSport(), httpCheck, httpVersion, headerUserAgent,requestCheck, payloadCheck, calendar.getTime());
                if (HttpGlobal.webCliUnitStuInfo.get(student.getSno()) != null) {
                    HttpGlobal.webCliUnitStuInfo.replace(student.getSno(), stuCliUnit);
                } else {
                    HttpGlobal.webCliUnitStuInfo.put(student.getSno(), stuCliUnit);
                }
            } else {
                StuCliUnit stuCliUnit = new StuCliUnit(student.getSname(), student.getSno(), student.getSip(), student.getSport(), false, false, false, false, false, calendar.getTime());
                if (HttpGlobal.webCliUnitStuInfo.get(student.getSno()) != null) {
                    HttpGlobal.webCliUnitStuInfo.replace(student.getSno(), stuCliUnit);
                } else {
                    HttpGlobal.webCliUnitStuInfo.put(student.getSno(), stuCliUnit);
                }
            }

        }
    }
}


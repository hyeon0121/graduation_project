package com.example.demo.server;

import com.example.demo.global.HttpGlobal;
import com.example.demo.model.StuCliGet;
import com.example.demo.model.StuCliPost;
import com.example.demo.model.StuCliScenario;
import com.example.demo.model.Student;
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

    public static class scenarioHandler implements HttpHandler {
        String getAnswer;
        Calendar calendar = Calendar.getInstance();
        StuCliScenario stuCliScenario;
        Student student;

        public scenarioHandler(String getAnswer, Student student) {
            this.getAnswer = getAnswer;
            this.student = student;
        }
        @Override
        public void handle(HttpExchange ex) throws IOException {

            if (ex.getRequestMethod().equals("GET")) {
                boolean httpCheck = false;
                boolean httpVersion = false;
                boolean headerUserAgent = false;

                String str = ex.getProtocol();
                String http = str.split("/")[0];
                String version = str.split("/")[1];

                if (http.equals("HTTP")) {
                    HttpGlobal.httpCheck = true;
                } else {
                    HttpGlobal.httpCheck = false;
                }

                if (version.equals("1.1")) {
                    HttpGlobal.httpVersion = true;
                } else {
                    HttpGlobal.httpVersion = false;
                }

                System.err.println(HttpGlobal.httpCheck);
                System.err.println(HttpGlobal.httpVersion);

                System.err.println("http : "+http);
                System.err.println("ver : "+version);

                System.err.println("Method : "+ex.getRequestMethod());

                Headers cliHeader2 = ex.getRequestHeaders();
                System.err.println("HEADER2 : "+cliHeader2.get("User-Agent"));

                if(cliHeader2.get("User-agent").toString().contains("ComputerNetwork")) {
                    HttpGlobal.headerUserAgent = true;
                    System.out.println("true");
                } else {
                    HttpGlobal.headerUserAgent = false;
                    System.out.println("false");
                }

                // DB
                stuCliScenario = new StuCliScenario(student.getSname(), student.getSno(), student.getSip(), student.getSport(), httpCheck, httpVersion, headerUserAgent, calendar.getTime());
                stuCliScenario.setGetAnswer(getAnswer);


                OutputStream out = ex.getResponseBody();
                String result = getAnswer;
                ex.sendResponseHeaders(200, result.getBytes().length);
                out.write(result.getBytes());
                out.close();
            }

            else if (ex.getRequestMethod().equals("POST")) {
                System.err.println("Method : "+ex.getRequestMethod());
                InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String json = br.readLine();


                try {
                    JSONObject obj = new JSONObject(json);
                    String sid = (String)obj.get("studentID");

                    Long baseNum =  Long.parseLong(sid);
                    Long tes = (long) (Math.random() * (1000));
                    Long testNum = baseNum - tes;

                    System.out.println(sid);
                    System.out.println(testNum);

                    HttpGlobal.postAnswer = testNum.toString();

                    //
                    stuCliScenario.setPostAnswer(testNum.toString());
                    if (HttpGlobal.webCliSceStuInfo.get(student.getSno()) != null) {
                        HttpGlobal.webCliSceStuInfo.replace(student.getSno(), stuCliScenario);
                    } else {
                        HttpGlobal.webCliSceStuInfo.put(student.getSno(), stuCliScenario);
                    }

                    OutputStream out = ex.getResponseBody();

                    ex.sendResponseHeaders(200, testNum.toString().length());

                    out.write(testNum.toString().getBytes());
                    out.close();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class getHandler implements HttpHandler {
        String getAnswer;
        Calendar calendar = Calendar.getInstance();
        Student student;

        public getHandler(String getAnswer, Student student) {
            this.getAnswer = getAnswer;
            this.student = student;
        }
        @Override
        public void handle(HttpExchange ex) throws IOException {

            if (ex.getRequestMethod().equals("GET")) {
                boolean httpCheck = false;
                boolean httpVersion = false;
                boolean headerUserAgent = false;

                String str = ex.getProtocol();
                String http = str.split("/")[0];
                String version = str.split("/")[1];

                if (http.equals("HTTP")) {
                    HttpGlobal.httpCheck = true;
                } else {
                    HttpGlobal.httpCheck = false;
                }

                if (version.equals("1.1")) {
                    HttpGlobal.httpVersion = true;
                } else {
                    HttpGlobal.httpVersion = false;
                }

                System.err.println(HttpGlobal.httpCheck);
                System.err.println(HttpGlobal.httpVersion);

                System.err.println("http : "+http);
                System.err.println("ver : "+version);

                System.err.println("Method : "+ex.getRequestMethod());

                Headers cliHeader2 = ex.getRequestHeaders();
                System.err.println("Header : "+cliHeader2.get("User-Agent"));

                if(cliHeader2.get("User-agent").toString().contains("ComputerNetwork")) {
                    HttpGlobal.headerUserAgent = true;
                    System.out.println("true");
                } else {
                    HttpGlobal.headerUserAgent = false;
                    System.out.println("false");
                }

                // DB
                StuCliGet stuCliGet = new StuCliGet(student.getSname(), student.getSno(), student.getSip(), student.getSport(), getAnswer, httpCheck, httpVersion, headerUserAgent, calendar.getTime());
                if (HttpGlobal.webCliGetStuInfo.get(student.getSno()) != null) {
                    HttpGlobal.webCliGetStuInfo.replace(student.getSno(), stuCliGet);
                } else {
                    HttpGlobal.webCliGetStuInfo.put(student.getSno(), stuCliGet);
                }

                OutputStream out = ex.getResponseBody();
                String result = getAnswer;
                ex.sendResponseHeaders(200, result.getBytes().length);
                out.write(result.getBytes());
                out.close();
            }

        }
    }

    public static class postHandler implements HttpHandler {
        Calendar calendar = Calendar.getInstance();
        Student student;
        public postHandler(Student student) {
            this.student = student;
        }

        @Override
        public void handle(HttpExchange ex) throws IOException {
            if (ex.getRequestMethod().equals("POST")) {
                boolean httpCheck = false;
                boolean httpVersion = false;
                boolean headerUserAgent = false;

                String str = ex.getProtocol();
                String http = str.split("/")[0];
                String version = str.split("/")[1];

                if (http.equals("HTTP")) {
                    HttpGlobal.httpCheck = true;
                } else {
                    HttpGlobal.httpCheck = false;
                }

                if (version.equals("1.1")) {
                    HttpGlobal.httpVersion = true;
                } else {
                    HttpGlobal.httpVersion = false;
                }

                System.err.println(HttpGlobal.httpCheck);
                System.err.println(HttpGlobal.httpVersion);

                System.err.println("http : "+http);
                System.err.println("ver : "+version);

                System.err.println("Method : "+ex.getRequestMethod());

                Headers cliHeader2 = ex.getRequestHeaders();
                System.err.println("Header : "+cliHeader2.get("User-Agent"));

                if(cliHeader2.get("User-agent").toString().contains("ComputerNetwork")) {
                    HttpGlobal.headerUserAgent = true;
                    System.out.println("true");
                } else {
                    HttpGlobal.headerUserAgent = false;
                    System.out.println("false");
                }

                InputStreamReader isr = new InputStreamReader(ex.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String json = br.readLine();

                try {
                    JSONObject obj = new JSONObject(json);
                    String sno = (String)obj.get("studentID");


                    Long baseNum =  Long.parseLong(sno);
                    Long tes = (long) (Math.random() * (1000));
                    Long testNum = baseNum - tes;

                    System.out.println(sno);
                    System.out.println(testNum);

                    HttpGlobal.postAnswer = testNum.toString();

                    // DB
                    StuCliPost stuCliPost = new StuCliPost(student.getSname(), student.getSno(), student.getSip(), student.getSport(), testNum.toString(), httpCheck, httpVersion, headerUserAgent, calendar.getTime());
                    if (HttpGlobal.webCliPostStuInfo.get(student.getSno()) != null) {
                        HttpGlobal.webCliPostStuInfo.replace(student.getSno(), stuCliPost);
                    } else {
                        HttpGlobal.webCliPostStuInfo.put(student.getSno(), stuCliPost);
                    }

                    OutputStream out = ex.getResponseBody();

                    ex.sendResponseHeaders(200, testNum.toString().length());

                    out.write(testNum.toString().getBytes());
                    out.close();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}


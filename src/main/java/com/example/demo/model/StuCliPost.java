package com.example.demo.model;

import java.util.Date;

public class StuCliPost extends Student {
    String postAnswer;

    boolean httpCheck, httpVersion, userAgent;
    Date accessTime;

    public StuCliPost(String sname, String sno, String sip, String sport, String postAnswer, boolean httpCheck, boolean httpVersion, boolean userAgent, Date accessTime) {
        super(sname, sno, sip, sport);
        this.postAnswer = postAnswer;
        this.httpCheck = httpCheck;
        this.httpVersion = httpVersion;
        this.userAgent = userAgent;
        this.accessTime = accessTime;
    }
}

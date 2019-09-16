package com.example.demo.model;

import java.util.Date;

public class StuCliUnit extends Student {

    boolean httpCheck, httpVersion, userAgent, requestCheck, payloadCheck;
    Date accessTime;


    public StuCliUnit(String sname, String sno, String sip, String sport, boolean requestCheck, boolean httpCheck, boolean httpVersion, boolean userAgent,
                     Date accessTime) {
        super(sname, sno, sip, sport);
        this.requestCheck = requestCheck;
        this.httpCheck = httpCheck;
        this.httpVersion = httpVersion;
        this.userAgent = userAgent;
        this.accessTime = accessTime;
    }

    public StuCliUnit(String sname, String sno, String sip, String sport, boolean httpCheck, boolean httpVersion, boolean userAgent, boolean requestCheck, boolean payloadCheck, Date accessTime) {
        super(sname, sno, sip, sport);
        this.httpCheck = httpCheck;
        this.httpVersion = httpVersion;
        this.userAgent = userAgent;
        this.requestCheck = requestCheck;
        this.payloadCheck = payloadCheck;
        this.accessTime = accessTime;
    }

    public boolean isPayloadCheck() {
        return payloadCheck;
    }

    public void setPayloadCheck(boolean payloadCheck) {
        this.payloadCheck = payloadCheck;
    }

    public boolean isRequestCheck() {
        return requestCheck;
    }

    public void setRequestCheck(boolean requestCheck) {
        this.requestCheck = requestCheck;
    }

    public boolean isHttpCheck() {
        return httpCheck;
    }

    public void setHttpCheck(boolean httpCheck) {
        this.httpCheck = httpCheck;
    }

    public boolean isHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(boolean httpVersion) {
        this.httpVersion = httpVersion;
    }

    public boolean isUserAgent() {
        return userAgent;
    }

    public void setUserAgent(boolean userAgent) {
        this.userAgent = userAgent;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }
}

package com.example.demo.model;

import java.util.Date;

public class StuCliScenario extends Student {
    String getAnswer, postAnswer;
    boolean httpCheck, httpVersion, userAgent;
    Date accessTime;

    public StuCliScenario(String sname, String sno, String sip, String sport, boolean httpCheck, boolean httpVersion, boolean userAgent, Date accessTime) {
        super(sname, sno, sip, sport);
        this.httpCheck = httpCheck;
        this.httpVersion = httpVersion;
        this.userAgent = userAgent;
        this.accessTime = accessTime;
    }

    public StuCliScenario(String sname, String sno, String sip, String sport, String postAnswer, Date accessTime) {
        super(sname, sno, sip, sport);
        this.postAnswer = postAnswer;
        this.accessTime = accessTime;
    }

    public String getGetAnswer() {
        return getAnswer;
    }

    public void setGetAnswer(String getAnswer) {
        this.getAnswer = getAnswer;
    }

    public String getPostAnswer() {
        return postAnswer;
    }

    public void setPostAnswer(String postAnswer) {
        this.postAnswer = postAnswer;
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

    @Override
    public String toString() {
        return "StuCliScenario{" +
                "getAnswer='" + getAnswer + '\'' +
                ", postAnswer='" + postAnswer + '\'' +
                ", httpCheck=" + httpCheck +
                ", httpVersion=" + httpVersion +
                ", userAgent=" + userAgent +
                ", accessTime=" + accessTime +
                ", sname='" + sname + '\'' +
                ", sno='" + sno + '\'' +
                ", sip='" + sip + '\'' +
                ", sport='" + sport + '\'' +
                '}';
    }
}

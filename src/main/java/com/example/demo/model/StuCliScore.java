package com.example.demo.model;

import org.apache.ibatis.type.Alias;

@Alias("web_client_scenario")
public class StuCliScore extends Student {
    boolean httpCheck, httpVersion, userAgent;
    boolean getScore, postScore;
    String date;

    public StuCliScore(String sname, String sno, String sip, String sport, boolean httpCheck, boolean httpVersion, boolean userAgent, boolean getScore, boolean postScore, String date) {
        super(sname, sno, sip, sport);
        this.httpCheck = httpCheck;
        this.httpVersion = httpVersion;
        this.userAgent = userAgent;
        this.getScore = getScore;
        this.postScore = postScore;
        this.date = date;
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

    public boolean isGetScore() {
        return getScore;
    }

    public void setGetScore(boolean getScore) {
        this.getScore = getScore;
    }

    public boolean isPostScore() {
        return postScore;
    }

    public void setPostScore(boolean postScore) {
        this.postScore = postScore;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "StuCliScore{" +
                "httpCheck=" + httpCheck +
                ", httpVersion=" + httpVersion +
                ", userAgent=" + userAgent +
                ", getScore=" + getScore +
                ", postScore=" + postScore +
                ", date='" + date + '\'' +
                ", sname='" + sname + '\'' +
                ", sno='" + sno + '\'' +
                ", sip='" + sip + '\'' +
                ", sport='" + sport + '\'' +
                '}';
    }
}

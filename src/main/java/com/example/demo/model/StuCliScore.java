package com.example.demo.model;

public class StuCliScore extends Student {
    boolean httpCheck, httpVersion, userAgent;
    int getScore, postScore;

    public StuCliScore(String sname, String sno, String sip, String sport, boolean httpCheck, boolean httpVersion, boolean userAgent) {
        super(sname, sno, sip, sport);
        this.httpCheck = httpCheck;
        this.httpVersion = httpVersion;
        this.userAgent = userAgent;
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

    public int getGetScore() {
        return getScore;
    }

    public void setGetScore(int getScore) {
        this.getScore = getScore;
    }

    public int getPostScore() {
        return postScore;
    }

    public void setPostScore(int postScore) {
        this.postScore = postScore;
    }
}

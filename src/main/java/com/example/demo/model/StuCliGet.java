package com.example.demo.model;

import java.util.Date;

public class StuCliGet extends Student{
    String getAnswer;
    boolean httpCheck, httpVersion, userAgent;
    Date accessTime;


    public StuCliGet(String sname, String sno, String sip, String sport, String getAnswer, boolean httpCheck, boolean httpVersion, boolean userAgent,
                     Date accessTime) {
        super(sname, sno, sip, sport);
        this.getAnswer = getAnswer;
        this.httpCheck = httpCheck;
        this.httpVersion = httpVersion;
        this.userAgent = userAgent;
        this.accessTime = accessTime;
    }

    @Override
    public String toString() {
        return "StuCliGet{" +
                "getAnswer='" + getAnswer + '\'' +
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

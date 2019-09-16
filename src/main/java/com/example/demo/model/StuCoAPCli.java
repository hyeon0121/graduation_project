package com.example.demo.model;

import java.util.Date;

public class StuCoAPCli extends Student {
    int msgScore, putScore, postScore;
    Date accessTime;


    public StuCoAPCli(String sname, String sno, String sip, String sport, int msgScore, int putScore, int postScore) {
        super(sname, sno, sip, sport);
        this.msgScore = msgScore;
        this.putScore = putScore;
        this.postScore = postScore;
    }
}

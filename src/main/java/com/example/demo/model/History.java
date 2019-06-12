package com.example.demo.model;

import org.apache.ibatis.type.Alias;

@Alias("history")
public class History {
    String sname;
    String sno;
    String sip;
    String sport;
    int msgScore;
    int maxScore;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSip() {
        return sip;
    }

    public void setSip(String sip) {
        this.sip = sip;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getMsgScore() {
        return msgScore;
    }

    public void setMsgScore(int msgScore) {
        this.msgScore = msgScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    @Override
    public String toString() {
        return "History{" +
                "sname='" + sname + '\'' +
                ", sno='" + sno + '\'' +
                ", sip='" + sip + '\'' +
                ", sport='" + sport + '\'' +
                ", msgScore=" + msgScore +
                ", maxScore=" + maxScore +
                '}';
    }
}

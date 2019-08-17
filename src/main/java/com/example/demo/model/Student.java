package com.example.demo.model;

public class Student {
    String sname;
    String sno;
    String sip;
    String sport;

    public Student(String sname, String sno, String sip, String sport) {
        this.sname = sname;
        this.sno = sno;
        this.sip = sip;
        this.sport = sport;
    }

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

    @Override
    public String toString() {
        return "Student{" +
                "sname='" + sname + '\'' +
                ", sno='" + sno + '\'' +
                ", sip='" + sip + '\'' +
                ", sport='" + sport + '\'' +
                '}';
    }
}

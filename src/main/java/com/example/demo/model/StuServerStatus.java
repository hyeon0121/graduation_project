package com.example.demo.model;

public class StuServerStatus extends Student {
    boolean connTest, multiThread, errorTest200, errorTest404, errorTest400;
    long elapsedTime;


    public StuServerStatus(String sname, String sno, String sip, String sport, boolean connTest, boolean multiThread, boolean errorTest200, boolean errorTest404, boolean errorTest400, long elapsedTime) {
        super(sname, sno, sip, sport);
        this.connTest = connTest;
        this.multiThread = multiThread;
        this.errorTest200 = errorTest200;
        this.errorTest404 = errorTest404;
        this.errorTest400 = errorTest400;
        this.elapsedTime = elapsedTime;
    }

    public boolean isConnTest() {
        return connTest;
    }

    public void setConnTest(boolean connTest) {
        this.connTest = connTest;
    }

    public boolean isMultiThread() {
        return multiThread;
    }

    public void setMultiThread(boolean multiThread) {
        this.multiThread = multiThread;
    }

    public boolean isErrorTest200() {
        return errorTest200;
    }

    public void setErrorTest200(boolean errorTest200) {
        this.errorTest200 = errorTest200;
    }

    public boolean isErrorTest404() {
        return errorTest404;
    }

    public void setErrorTest404(boolean errorTest404) {
        this.errorTest404 = errorTest404;
    }

    public boolean isErrorTest400() {
        return errorTest400;
    }

    public void setErrorTest400(boolean errorTest400) {
        this.errorTest400 = errorTest400;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}

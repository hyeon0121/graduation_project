package com.example.demo.model;

public class StuServerHeader extends Student{
    boolean connTest, multiThread, contentLengthTest, contentHtmlTest, contentImageTest;
    long elapsedTime;

    public StuServerHeader(String sname, String sno, String sip, String sport, boolean connTest, boolean multiThread, boolean contentLengthTest, boolean contentHtmlTest, boolean contentImageTest, long elapsedTime) {
        super(sname, sno, sip, sport);
        this.connTest = connTest;
        this.multiThread = multiThread;
        this.contentLengthTest = contentLengthTest;
        this.contentHtmlTest = contentHtmlTest;
        this.contentImageTest = contentImageTest;
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

    public boolean isContentLengthTest() {
        return contentLengthTest;
    }

    public void setContentLengthTest(boolean contentLengthTest) {
        this.contentLengthTest = contentLengthTest;
    }

    public boolean isContentHtmlTest() {
        return contentHtmlTest;
    }

    public void setContentHtmlTest(boolean contentHtmlTest) {
        this.contentHtmlTest = contentHtmlTest;
    }

    public boolean isContentImageTest() {
        return contentImageTest;
    }

    public void setContentImageTest(boolean contentImageTest) {
        this.contentImageTest = contentImageTest;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}

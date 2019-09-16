package com.example.demo.client;

import com.example.demo.global.HttpGlobal;
import com.example.demo.model.StuServerInfo;
import com.example.demo.model.StuServerStatus;
import com.example.demo.model.Student;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;

public class ClientStatusHandler extends SimpleChannelInboundHandler<HttpObject> {
    private String hostIP;
    private Student student;
    public int itemIndex;
    private static boolean connTest = false;
    private static boolean multiThread = false;
    private static boolean errorTest200 = false;
    private static boolean errorTest404 = false;
    private static boolean errorTest400 = false;


    public ClientStatusHandler(Student student, String hostIP, int index) {
        this.student = student;
        this.hostIP= hostIP;
        this.itemIndex = index;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;

            String sno = student.getSno();
            // System.out.println("msg:   " +msg);

            int checkSeq = itemIndex;
            String[] statusArr = response.getStatus().toString().split(" ");
            int status = Integer.parseInt(statusArr[0]);

            System.out.println("---");
            System.out.println(response.getStatus());
            System.out.println(status);
            System.out.println("checkSeq "+checkSeq);


            switch (checkSeq) {
                case 0:
                    if (status == 200 || status == 404 || status == 501) {
                        connTest = true;
                        HttpGlobal.statusMap.put(hostIP, 1);

                    } else {
                        connTest = false;

                    }
                    break;
                // MultiThread
                case 2:
                    long tmp = System.currentTimeMillis() - HttpGlobal.statusStartTime.get(sno);

                    System.out.println("Total elapsed http request/response time in milliseconds: " + tmp);

                    if (HttpGlobal.statusElapsedTime.get(sno) != null) {
                        HttpGlobal.statusElapsedTime.replace(sno, tmp);
                    } else {
                        HttpGlobal.statusElapsedTime.put(sno, tmp);
                    }

                    if (status == 200) {
                        multiThread = true;
                        errorTest200 = true;

                    } else {
                        multiThread = false;
                        errorTest200 = false;
                    }

                    break;
                case 3:

                    if (status == 404) {
                        errorTest404 = true;

                    } else {
                        errorTest404 = false;
                    }

                    break;
                case 4:

                    if (status == 400) {
                        errorTest400 = true;

                        StuServerStatus stuVal = new StuServerStatus(this.student.getSname(), this.student.getSno(), this.student.getSip(), this.student.getSport(),
                                connTest, multiThread, errorTest200, errorTest404, errorTest400, HttpGlobal.statusElapsedTime.get(sno));

                        stuVal.toString();

                        HttpGlobal.statusInfo.put(this.student.getSno(), stuVal);

                    } else {
                        errorTest400 = false;

                        StuServerStatus stuVal = new StuServerStatus(this.student.getSname(), this.student.getSno(), this.student.getSip(), this.student.getSport(),
                                connTest, multiThread, errorTest200, errorTest404, errorTest400,HttpGlobal.statusElapsedTime.get(sno));

                        stuVal.toString();

                        HttpGlobal.statusInfo.put(this.student.getSno(), stuVal);
                    }

                    break;

            }

        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
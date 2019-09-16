package com.example.demo.client;

import com.example.demo.global.HttpGlobal;
import com.example.demo.model.StuServerHeader;
import com.example.demo.model.Student;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;

public class ClientHeaderHandler extends SimpleChannelInboundHandler<HttpObject> {
    private String hostIP;
    private Student student;
    public int itemIndex;
    private static boolean connTest = false;
    private static boolean multiThread = false;
    private static boolean contentLengthTest = false;
    private static boolean contentHtmlTest = false;
    private static boolean contentImageTest = false;


    public ClientHeaderHandler(Student student, String hostIP, int index) {
        this.student = student;
        this.hostIP= hostIP;
        this.itemIndex = index;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;

            String sno = student.getSno();
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
                // MultiThread & Content length
                case 2:
                    long tmp = System.currentTimeMillis() - HttpGlobal.headerStartTime.get(sno);

                    System.out.println("Total elapsed http request/response time in milliseconds: " + tmp);

                    if (HttpGlobal.headerElapsedTime.get(sno) != null) {
                        HttpGlobal.headerElapsedTime.replace(sno, tmp);
                    } else {
                        HttpGlobal.headerElapsedTime.put(sno, tmp);
                    }

                    System.out.println("CASE2");

                    // MultiThread check
                    if (status == 200) {
                        multiThread = true;
                    } else {
                        multiThread = false;
                    }

                    String contentType="";

                    if(response.headers().get("Content-Type") != null) {
                        contentType = response.headers().get("Content-Type");

                    }else if(response.headers().get("content-type") != null) {
                        contentType = response.headers().get("content-type");

                    }

                    if (response.headers().get("Content-Length") != null) {

                        int contentLength = Integer.parseInt(response.headers().get("Content-Length"));

                        if ((status == 200) && (contentLength == 1024)) {

                            System.err.println("content length "+contentLength);

                            contentLengthTest = true;

                        } else {

                            System.err.println("content length"+contentLength);

                            contentLengthTest = false;
                        }

                        if ((status == 200) && ((contentType.equals("text/html")) || (contentType.equals("text-html")))) {
                            contentHtmlTest = true;
                        } else {
                            contentHtmlTest = false;
                        }

                        break;
                    } else {
                        System.err.println("content length"+ "IS NULL");
                        contentLengthTest = false;

                        break;
                    }

                case 3:

                    System.out.println("CASE3");

                    contentType="";

                    if(response.headers().get("Content-Type") != null) {
                        contentType = response.headers().get("Content-Type");

                    }else if(response.headers().get("content-type") != null) {
                        contentType = response.headers().get("content-type");

                    }

                    if ((status == 200) && ((contentType.equals("image/jpg")) || (contentType.equals("image/jpeg")))) {
                        contentImageTest = true;

                    } else {
                        contentImageTest = false;
                    }

                    if (status == 200) {
                        StuServerHeader stuVal = new StuServerHeader(this.student.getSname(), this.student.getSno(), this.student.getSip(), this.student.getSport(),
                                connTest, multiThread, contentLengthTest, contentHtmlTest, contentImageTest, HttpGlobal.headerElapsedTime.get(sno));

                        stuVal.toString();

                        HttpGlobal.headerInfo.put(this.student.getSno(), stuVal);
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
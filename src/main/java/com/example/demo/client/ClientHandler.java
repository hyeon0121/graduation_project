package com.example.demo.client;

import com.example.demo.global.HttpGlobal;
import com.example.demo.model.StuServerInfo;
import com.example.demo.model.Student;
import com.sun.net.httpserver.HttpExchange;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends SimpleChannelInboundHandler<HttpObject> {
	public static String recvStatus;
	private String hostIP;
	private Student student;
	public int itemIndex;
	private static boolean connTest = false;
	private static boolean multiThread = false;
	private static boolean errorTest200 = false;
	private static boolean errorTest201 = false;
	private static boolean errorTest404 = false;
	private static boolean errorTest400 = false;
	private static boolean contentLengthTest = false;
	private static boolean contentHtmlTest = false;
	private static boolean contentImageTest = false;

	public ClientHandler(Student student, String hostIP, int index) {
		this.student = student;
		this.hostIP= hostIP;
		this.itemIndex = index;
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

		if (msg instanceof HttpResponse) {
			HttpResponse response = (HttpResponse) msg;

			System.out.println("msg:   " +msg);

			int checkSeq = itemIndex;
			String[] statusArr = response.getStatus().toString().split(" ");
			int status = Integer.parseInt(statusArr[0]);
			
			System.out.println("---");
			System.out.println(response.getStatus());
			System.out.println(status);
			System.out.println("checkSeq"+checkSeq);
			
			
			switch (checkSeq) {
				case 0:
					if (status == 200 || status == 201 || status == 404 || status == 501) {
						connTest = true;
						HttpGlobal.statusMap.put(hostIP, 1);

					} else {
						connTest = false;

					}
					break;
				// MultiThread
				case 2:
					// System.out.println("1");
					//
					if (status == 200) {
						multiThread = true;
						errorTest200 = true;

					} else {
						multiThread = false;
						errorTest200 = false;
					}
					break;
				case 3:
					// System.out.println("3");
					if (status == 201) {
						errorTest201 = true;

					} else {
						errorTest201 = false;
					}
					break;

				case 4:
					// System.out.println("3");

					if (status == 404) {
						errorTest404 = true;

					} else {
						errorTest404 = false;
					}
					break;

				case 5:
					if (status == 400) {
						errorTest400 = true;

					} else {
						errorTest400 = false;
					}

					break;

				case 6:
					System.out.println("CASE6");

					String contentType="";

					if(response.headers().get("Content-Type")!=null) {
						contentType = response.headers().get("Content-Type");

					}else if(response.headers().get("content-type")!=null) {
						contentType = response.headers().get("content-type");

					}

					if (response.headers().get("Content-Length")!=null) {

						int contentLength = Integer.parseInt(response.headers().get("Content-Length"));
						System.err.println("content length"+contentLength);

						if ((status == 200) && (contentLength == 1024)) {

							System.err.println("content length"+contentLength);

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

				case 7:

					contentType="";

					if(response.headers().get("Content-Type")!=null) {
						contentType = response.headers().get("Content-Type");

					}else if(response.headers().get("content-type")!=null) {
						contentType = response.headers().get("content-type");

					}


					if ((status == 200) && ((contentType.equals("image/jpg"))||(contentType.equals("image/jpeg")))) {
						contentImageTest = true;

					} else {
						contentImageTest = false;
					}

					StuServerInfo stuVal = new StuServerInfo(this.student.getSname(), this.student.getSno(), this.student.getSip(), this.student.getSport(),
							connTest, multiThread, errorTest200, errorTest201, errorTest404,
							errorTest400, contentLengthTest, contentHtmlTest, contentImageTest);

					stuVal.toString();

					HttpGlobal.stuInfo.put(this.student.getSno(), stuVal);

					long lTime = System.currentTimeMillis();

					SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

					StuServerInfo userInfo = HttpGlobal.stuInfo.get(student.getSno());

					String str = dayTime.format(new Date(lTime));
					int cnt = 0;

					int checkScore = 20;

					if(cnt == 0) {

						if(userInfo.isConnTest()) {
							checkScore += 10;
							System.out.println(1);
						}
						if(userInfo.isMultiThread()) {
							checkScore += 10;
							System.out.println(2);

						}

						if(userInfo.isErrorTest200()) {
							System.out.println(3);

							checkScore += 10;
						}

						if(userInfo.isErrorTest201()) {
							System.out.println(4);

							checkScore += 10;
						}

						if(userInfo.isErrorTest404()) {
							System.out.println(5);

							checkScore += 10;
						}

						if(userInfo.isErrorTest400()) {
							System.out.println(6);

							checkScore += 10;
						}

						if(userInfo.isContentLengthTest()) {
							System.out.println(7);

							checkScore += 10;
						}

						if(userInfo.isContentHtmlTest()) {
							System.out.println(8);

							checkScore += 10;
						}

						if(userInfo.isContentImageTest()) {
							System.out.println(9);
							checkScore += 10;
						}
					}

					String score = String.valueOf(checkScore);

					System.out.println("################# score :" + score + "      time  :  " + str);

					break;

			}

		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	public String getRecvStatus() {
		return recvStatus;
	}

	public void setRecvStatus(String recvStatus) {
		this.recvStatus = recvStatus;
	}

}
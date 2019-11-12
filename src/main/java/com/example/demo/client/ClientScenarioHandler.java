package com.example.demo.client;

import com.example.demo.global.HttpGlobal;
import com.example.demo.model.StuServerInfo;
import com.example.demo.model.Student;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ClientScenarioHandler extends SimpleChannelInboundHandler<HttpObject> {
	Calendar calendar = Calendar.getInstance();
	private String hostIP;
	private Student student;
	public int itemIndex;
	private String cookieValue;
	private static boolean connTest = false;
	private static boolean multiThread = false;
	private static boolean errorTest200 = false;
	private static boolean errorTest404 = false;
	private static boolean errorTest400 = false;
	private static boolean contentLengthTest = false;
	private static boolean contentHtmlTest = false;
	private static boolean contentImageTest = false;
	private static boolean cookieTest = false;

	public ClientScenarioHandler(Student student, String hostIP, int index) {
		System.out.println("### handler test : " + student + " : " + hostIP +" : " + index);
		this.student = student;
		this.hostIP= hostIP;
		this.itemIndex = index;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("active");
		super.channelActive(ctx);

	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		if (msg instanceof HttpResponse) {
			HttpResponse response = (HttpResponse) msg;

			String sno = this.student.getSno();

			int checkSeq = itemIndex;
			String[] statusArr = response.getStatus().toString().split(" ");
			int status = Integer.parseInt(statusArr[0]);
			
			System.out.println("---");
			System.out.println(response.getStatus());
			System.out.println(status);
			System.out.println("checkSeq "+checkSeq);

			switch (checkSeq) {
				case 0:
					if (response.headers().get("Set-Cookie") != null) {
						String cookies[] = response.headers().get("Set-Cookie").split(";");
						String cookie[] = cookies[0].split("=");

						String key = cookie[0];
						cookieValue = cookie[1];

						System.out.println("key:" + key + ", value:" + cookieValue);
						cookieTest = true;

					} else {
						cookieTest = false;
					}

					if (status == 200 || status == 404 || status == 501) {
						connTest = true;
						HttpGlobal.statusMap.put(hostIP, 1);

					} else {
						connTest = false;
					}
					break;
				// MultiThread
				case 2:
					long tmp = System.currentTimeMillis() - HttpGlobal.sceStartTime.get(sno);

					System.out.println("Total elapsed http request/response time in milliseconds: " + tmp);

					if (HttpGlobal.sceElapsedTime.get(sno) != null) {
						HttpGlobal.sceElapsedTime.replace(sno, tmp);
					} else {
						HttpGlobal.sceElapsedTime.put(sno, tmp);
					}

					cookieTest = cookieValueTest(response);

					if (status == 200) {
						multiThread = true;
						errorTest200 = true;

					} else {
						multiThread = false;
						errorTest200 = false;
					}

					break;
				case 3:
					cookieTest = cookieValueTest(response);

					if (status == 404) {
						errorTest404 = true;

					} else {
						errorTest404 = false;
					}

					break;

				case 4:
					cookieTest = cookieValueTest(response);

					if (status == 400) {
						errorTest400 = true;

					} else {
						errorTest400 = false;
					}

					break;

				case 5:
					cookieTest = cookieValueTest(response);

					System.out.println("CASE5");

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

				case 6:
					cookieTest = cookieValueTest(response);

					System.out.println("CASE6");

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

					System.out.println("Total Time" + HttpGlobal.sceElapsedTime.get(sno));

					if (status == 200) {
						StuServerInfo stuVal = new StuServerInfo(this.student.getSname(), sno, this.student.getSip(), this.student.getSport(),
								connTest, multiThread, errorTest200, errorTest404,
								errorTest400, contentLengthTest, contentHtmlTest, contentImageTest, cookieTest, HttpGlobal.sceElapsedTime.get(sno));

						// 테스트 완료 시간 설정
						DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm");
						Date d = new Date();
						stuVal.setDate(formatter.format(d));

						stuVal.toString();

						HttpGlobal.stuInfo.put(this.student.getSno(), stuVal);
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


	public boolean cookieValueTest(HttpResponse response) {
		boolean cookieValueTest;
		if (response.headers().get("Set-Cookie") != null && cookieTest) {
			String cookies[] = response.headers().get("Set-Cookie").split(";");
			String cookie[] = cookies[0].split("=");

			String key = cookie[0];
			String value = cookie[1];

			System.out.println("key:" + key + ", value:" + value);

			if (value.equals(cookieValue)) {
				cookieValueTest = false;
			} else {
				cookieValue = value;
				cookieValueTest = true;
			}

		} else {
			cookieValueTest = false;
		}

		return cookieValueTest;
	}
}
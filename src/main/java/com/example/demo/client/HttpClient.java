package com.example.demo.client;

import com.example.demo.model.Student;
import com.sun.net.httpserver.HttpExchange;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class HttpClient extends Thread {
	public static boolean connectionRefuse = false;

	public int itemIndex;
	HttpExchange he;
	public String host;
	public int port;

	public Student student;

	public HttpClient(Student student, int index) {
		this.student = student;
		this.host = student.getSip();
		this.port = Integer.parseInt(student.getSport());
		this.itemIndex = index;
	}

	@Override
	public void run() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {

			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new HttpResponseDecoder());
					ch.pipeline().addLast(new HttpRequestEncoder());
					ch.pipeline().addLast(new ClientHandler(student, host, itemIndex));

				}
			});

			// Start the client
			ChannelFuture f;

			f = b.connect(host, port).sync();

			String msg = "";
			DefaultFullHttpRequest request;

			URI uri = null;
			switch (itemIndex) {

				case 0:
					uri = new URI("/" + "index.html");
					break;
				case 1:
					uri = new URI("/" + "index.html");
					break;
				case 2:
					uri = new URI("/" + "index.html");
					break;
				case 3:
					uri = new URI("/" + "index.html");
					break;
				case 4:
					uri = new URI("/mir.html");
					break;
				case 5:
					uri = new URI("/" + "index.html");
					break;
				case 6:
					uri = new URI("/" + "index.html");
					break;
				case 7:
					uri = new URI("/" + "image.jpg");
					break;
			}

			if (itemIndex == 5) {
				request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, uri.toASCIIString(),
						Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
			} else{
				request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString(),
						Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
			}

			request.headers().set(HttpHeaders.Names.HOST, host);
			request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
			request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());

			f.channel().writeAndFlush(request);
			f.channel().closeFuture().sync();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			workerGroup.shutdownGracefully();

		}

	}
}

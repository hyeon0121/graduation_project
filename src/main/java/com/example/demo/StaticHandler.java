package com.example.demo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange ex) throws IOException {

    }

    public static int findFreePort() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            socket.setReuseAddress(true);
            int port = socket.getLocalPort();
            try {
                socket.close();
            } catch (IOException e) {
                // Ignore IOException on close()
            }
            return port;
        } catch (IOException e) {
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        throw new IllegalStateException("Could not find a free TCP/IP port to start embedded Jetty HTTP Server on");
    }

    public static long testNumber() {
        long baseNum = 2017102889;
        long tes = (long) (Math.random() * (5 - 2)) + 2;
        long testNum = baseNum * tes;

        return testNum;
    }

    public static Long[] mixNumberSeq2(Long ansNum, Long stuNum) {
        Long[] baseArr = new Long[4];

        int a = (int) (Math.random() * (5 - 1)) + 1;

        if (a == 1) {

            baseArr[0] = ansNum-1;

            baseArr[1] = stuNum + 100000;

            baseArr[2] = stuNum - 2017102889;

            baseArr[3] = stuNum + 2017102889;

        } else if (a == 2) {

            baseArr[1] = ansNum-1;

            baseArr[0] = stuNum + 100000;

            baseArr[2] = stuNum - 2017102889;

            baseArr[3] = stuNum + 2017102889;

        } else if (a == 3) {
            baseArr[0] = stuNum - 2017102889;


            baseArr[1] = stuNum +100000;
            baseArr[2] = ansNum-1;


            baseArr[3] = stuNum + 2017102889;

        } else if (a == 4) {

            baseArr[3] = ansNum-1;

            baseArr[1] = stuNum +100000;

            baseArr[2] = stuNum - 2017102889;

            baseArr[0] = stuNum + 2017102889;



        }

        return baseArr;
    }


}

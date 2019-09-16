package com.example.demo.client;

import com.example.demo.global.HttpGlobal;
import com.example.demo.model.Student;

public class StatusCliCnt extends Thread {
    StatusHttpClient httpClient;
    Student student;

    public StatusCliCnt(Student student) {
        this.student = student;
    }

    public void run() {
        System.out.println();
        System.out.println("============");
        System.out.println(student.getSip());
        System.out.println(student.getSport());

        httpClient = new StatusHttpClient(student, 0);
        httpClient.start();

        while (true) {
            try {
                sleep(1000);

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (HttpGlobal.statusMap.get(student.getSip()) != null) {
                if (HttpGlobal.statusMap.get(student.getSip()) == 1) {
                    for (int i = 1; i < 5; i++) {
                        httpClient = new StatusHttpClient(student,i);
                        HttpGlobal.statusMap.put(student.getSip(), i);

                        httpClient.start();

                        try {
                            sleep(100);

                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

                break;
            }
        }

        System.out.println("out");

    }
}


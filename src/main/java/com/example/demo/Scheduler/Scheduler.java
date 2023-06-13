package com.example.demo.Scheduler;

import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {

    public static void main(String[] args) {
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };

        // 3초 후부터 2초마다 작업을 실행
        timer.schedule(task, 3000, 2000);
    }
}

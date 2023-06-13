package com.example.demo.Scheduler;

import java.util.Timer;
import java.util.TimerTask;

//schedule() 메서드를 사용하여 작업을 스케줄링합니다.
// 스케줄러를 사용하면 특정 시간 간격 또는 일정 시간에 작업을 실행할 수 있습니다.
// Timer 클래스는 단일 스레드를 사용하여 작업을 처리하므로 간단한 스케줄링 작업에 적합합니다.
// 동시에 처리할 수는 없습니다.
public class Scheduler {

    public static void main(String[] args) {
        //주어진 시간 간격에 따라 작업을 예약하고 실행하는 데 사용
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

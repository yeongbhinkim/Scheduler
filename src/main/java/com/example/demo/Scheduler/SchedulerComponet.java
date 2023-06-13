package com.example.demo.Scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerComponet {
    /**
     * Scheduler는 기본적으로 thread 1개를 이용하여 동기 형식으로 진행됩니다.
     * 즉 1번 스케줄이 끝나지 않으면 2번 스케줄이 시작시간이 되었다고 하더라도 시작되지 않습니다.
     * @throws Exception
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void SchedulerComponet() throws Exception {
        System.out.println("SchedulerComponet");
    }
}

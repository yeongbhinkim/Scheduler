package com.example.demo.Scheduler;

import org.quartz.*;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

//https://sabarada.tistory.com/113
public class SchedulerQuartz {
    /**
     * Quartz의 Job의 필수적인 요소는 Job, JobDetail, Trigger로 3가지입니다.
     *
     * @param args
     * @throws SchedulerException
     */
    public static void main(String[] args) throws SchedulerException {
        // 스케줄러 팩토리 생성
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        // Job 상세 정보 생성
        //Job : 실제로 실행되는 로직이 있는 곳입니다. Quartz에서 interface로 제공하며 해당 interface를 구현하면됩니다.
        //JobDetail : Job을 실행시키기 위한 구체적인 정보를 가지고 있는 인스턴스입니다.
        // JobBuilder API를 통해 만들 수 있습니다. Job에 대한 설명 Job의 ID 등을 설정할 수 있습니다.
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                .withIdentity("myJob", "group1")
                .build();

        // CronTrigger 생성
        //Trigger : Trigger는 Job이 실행되는 실행 조건을 가지고 있는 인스턴스입니다. TriggerBuilder API를 통해 만들 수 있습니다.
        // 조건으로 단순히 특정 시간 간격으로 할 수 있으며 Cron으로도 작성할 수 있습니다.
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")) // Cron 표현식
                .build();

        // 스케줄러에 Job과 Trigger 등록
        scheduler.scheduleJob(jobDetail, cronTrigger);

        // 스케줄러 시작
        scheduler.start();
    }

    public static class MyJob implements Job {
        public void execute(JobExecutionContext context) throws JobExecutionException {
            // 현재 시간 출력
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            System.out.println("SchedulerQuartz " + dateFormat.format(now));
        }
    }
}

/**
 * Scheduler간의 Clustering 기능
 * 여러 Service 노드가 있을 때 해당 노드들의 Scheduler간의 Clustering을 책임져 줄 수 있습니다. JobStore 를 이용하며 Memory 방식, DB 방식을 지원합니다. 클러스터링 방식은 랜덤 선택입니다.
 *
 * Scheculer 실패에 대해서 후처리 기능
 * Misfire Instructions 기능을 제공합니다. Misfire Instructions 기능이란 만약 스케줄러가실패했을 때나 thread pool에서 사용할 수 있는 thread가 없는 경우에 발생할 수 있습니다.
 *
 * 기타
 * JVM 종료 이벤트를 캐치하여 스케줄러에게 종료를 알려주는 기능도 제공합니다.
 * 여러가지 기본 Plug-in을 제공합니다.
 * Job, Trigger 실행에 대해서 이벤트 처리를 할 수 있습니다.
 * 마무리
 * 결과적으로 만약 단순한 Scheduling에 따른 작업이 필요하시다면 단연코 Spring Scheduler를 추천합니다. Spring Quartz는 좀 더 Scheduling의 세밀한 제어가 필요할 때, 그리고 만들어야하는 Scheduling 서비스 노드가 멀티이기 때문에 클러스터링이 필요할 때 여러분이 만들고자 하는 서비스에 도움이 될 것입니다.
 *
 * 기능을 구현하기 위해 많은 라이브러리가 나와 있으며 우리는 그 중 우리에게 적합한 라이브러리를 잘 선택하여 프로젝트를 진행하여야 합니다. 그렇지 않다면 프로젝트를 마무리 하고자 했던 시간에 맞추지 못할 수 있고 또는 퍼포먼스적으로 기대에 못 미칠 수 있기 때문입니다.
 *
 *
 *
 *
 *
 *
 *
 *
 * Quartz는 Java 기반의 강력한 스케줄링 라이브러리로, 복잡한 스케줄링 작업을 수행할 수 있습니다. 아래에서 Quartz의 기능과 장단점에 대해 설명드리겠습니다:
 *
 * 기능:
 *
 * 유연한 스케줄링: Quartz는 Cron 표현식을 사용하여 매우 유연한 스케줄링을 지원합니다. 다양한 실행 주기를 정확하게 제어할 수 있어, 초 단위부터 연 단위까지 다양한 주기로 작업을 실행할 수 있습니다.
 * 분산 환경 지원: Quartz는 여러 서버 또는 클러스터에서 작업을 분산하여 실행할 수 있는 분산 환경을 지원합니다. 이를 통해 고가용성과 확장성을 제공할 수 있습니다.
 * 강력한 예약 기능: Quartz는 예약된 작업을 수행하기 위해 정확한 시간을 추적하고 작업을 실행할 수 있습니다. 작업이 예상 시간보다 오래 걸릴 경우에도 예약된 작업이 정확하게 수행됩니다.
 * 작업 상태 및 이력 관리: Quartz는 작업의 상태와 실행 이력을 추적하고 관리할 수 있는 기능을 제공합니다. 이를 통해 작업의 성공, 실패 및 예외 처리 상황을 모니터링할 수 있습니다.
 * 장점:
 *
 * 강력한 기능: Quartz는 매우 다양한 스케줄링 요구사항을 지원하며, 다양한 유형의 작업을 스케줄링할 수 있습니다. 복잡한 작업 흐름이나 시간 기반 작업을 처리하는 데 탁월합니다.
 * 확장성과 고가용성: Quartz는 분산 환경에서 작업을 실행할 수 있으며, 여러 서버 또는 클러스터에 작업을 분산하여 고가용성과 확장성을 제공합니다.
 * 안정성: Quartz는 안정적이고 신뢰할 수 있는 스케줄링 솔루션으로 알려져 있습니다. 많은 개발자들이 사용하고 있으며, 검증된 기술과 도구로 지원됩니다.
 * 단점:
 *
 * 학습 곡선: Quartz는 강력한 기능을 가지고 있어 초기 설정 및 사용에 약간의 학습 곡선이 존재할 수 있습니다. 특히 복잡한 작업 및 분산 환경에서의 구성은 추가적인 학습과 이해가 필요할 수 있습니다.
 * Quartz는 다양한 유형의 스케줄링 작업을 처리할 수 있는 강력한 도구입니다. 복잡한 작업 흐름이나 분산 환경에서의 스케줄링을 필요로 할 때, Quartz는 신뢰성과 확장성을 제공하는 선택지 중 하나입니다.
 *
 *
 *
 *
 *
 *
 */
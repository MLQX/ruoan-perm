//package com.example.quartz.demo.config;
//
//import com.example.quartz.demo.job.MyCronJob;
//import com.example.quartz.demo.job.MyJob;
//import org.quartz.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class QuartzConfiguration {
//    /**
//     * 简单任务
//     * @return
//     */
//    @Bean
//    public JobDetail myJobDetail(){
//        return JobBuilder.newJob(MyJob.class).withDescription("myJob").storeDurably().build();
//    }
//    @Bean
//    public Trigger myJobTrigger(){
//        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds(15).repeatForever();
//        return TriggerBuilder.newTrigger().forJob(myJobDetail()).withIdentity("myJobTrigger").withSchedule(simpleScheduleBuilder).build();
//    }
//
//    /**
//     * cron 任务
//     */
//    @Bean
//    public JobDetail myCronJobDetail(){
//        return JobBuilder.newJob(MyCronJob.class).withDescription("myCronJob").storeDurably().build();
//    }
//    @Bean
//    public Trigger myCronJobTrigger(){
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
//        return TriggerBuilder.newTrigger().forJob(myCronJobDetail()).withIdentity("myCronJobTrigger").withSchedule(cronScheduleBuilder).build();
//    }
//}

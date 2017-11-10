package com.rrd.schedule;

import org.apache.log4j.Logger;
import org.quartz.*;

import java.util.Date;
import java.util.List;

/**
 * //定时器统一调度管理类
 * Created by xhb on 2017/6/23.
 */
public class ScheduleManager {
    private static Logger logger = Logger.getLogger(ScheduleManager.class);
    /**
     * 添加一个定时任务
     * @param scheduler ：调度器
     * @param jobName ：任务名
     * @param clazz ：任务
     * @param time ：时间设置
     */
    public static void addJob(Scheduler scheduler, String jobName, String jobGroup,
                              String triggerName, String triggerGroup,
                              @SuppressWarnings("rawtypes") Class clazz, String time, JobDataMap jobDataMap){
        try {
            //生成工作
            JobDetail jobDetail;
            if(jobDataMap == null) {
                jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, jobGroup).build();
            }else{
                jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, jobGroup)
                        .setJobData(jobDataMap).build();
            }
            //生成触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(triggerName, triggerGroup)
                    .withSchedule(CronScheduleBuilder.cronSchedule(time))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("一个任务加入；任务执行表达式为："+time);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static boolean isExist (Scheduler scheduler, String jobName, String jobGroup) throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroup));
        if(jobDetail == null) {
            return false;
        }else {
            return true;
        }
    }

    /**
     * 修改一个任务的触发时间
     * @param scheduler
     * @param
     * @param time
     */
    public static void modifyJobTime(Scheduler scheduler, String triggerName, String triggergroup, String time) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggergroup);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if(trigger == null) {
                return ;
            }
            String oldTime = trigger.getCronExpression();
            if(!oldTime.equals(time)) {
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerName, triggergroup)
                        .withSchedule(CronScheduleBuilder.cronSchedule(time))
                        .build();
                scheduler.scheduleJob(trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 移除指定任务
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @param triggerName
     * @param triggerGroup
     */
    public static void removeJob(Scheduler scheduler, String jobName, String jobGroup, String triggerName, String triggerGroup) {
        try {
            //停止触发器
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
            scheduler.pauseTrigger(triggerKey);
            //移除触发器
            scheduler.unscheduleJob(triggerKey);
            //删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动所有定时任务
     */
    public static void startJobs(Scheduler scheduler){
        try {
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     * @param scheduler
     */
    public static void shutdowJobs(Scheduler scheduler) {
        try {
            if(!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取指定任务的下次执行时间
     * @param triggerName
     * @param triggerGroup
     * @return
     */
    public static Date getNextExecuteDate(Scheduler scheduler, String triggerName, String triggerGroup) {
        try {
            Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroup));
            if(trigger == null){
                return null;
            }
            return trigger.getFinalFireTime();
        } catch (SchedulerException e) {
            e.printStackTrace();
            logger.error("获取任务执行时间失败；"+e);
            return null;
        }
    }

    /**
     * 判断当前任务是否正在被执行
     * @param scheduler
     * @param jobName
     * @param jobGroup
     * @return
     */
    public static boolean isExecuteWithTask(Scheduler scheduler, String jobName, String jobGroup){
        try {
            List<JobExecutionContext> jobExecutionContextList =  scheduler.getCurrentlyExecutingJobs();
            boolean executeing = false;
            for(JobExecutionContext jobExecutionContext : jobExecutionContextList) {
                if(jobExecutionContext.getJobDetail().getKey().equals(JobKey.jobKey(jobName, jobGroup))){
                    executeing = true;
                }
            }
            return executeing;
        } catch (SchedulerException e) {
            logger.error("获取任务执行状态失败；"+e);
            throw new RuntimeException("获取任务执行状态失败"+e);
        }
    }
}

package com.rrd.listener;

import com.rrd.schedule.ComThreadPool;
import com.rrd.schedule.ScheduleManager;
import com.rrd.schedule.tasks.BillJob;
import com.rrd.socket.MegSocketHandler;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Administrator on 2017/6/27.
 */

public class CommonListener implements ServletContextListener {

    private static Logger logger = Logger.getLogger(CommonListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext context =  ContextLoader.getCurrentWebApplicationContext();
        Scheduler scheduler = context.getBean(Scheduler.class);
        //添加定时任务
        String cron = "2 0 0 1/1 * ? *";  //设置每晚00:00:02定时执行
        ScheduleManager.addJob(scheduler, "job_sync_bill","job_bill",
                "trg_sync_bill", "trg_bill", BillJob.class, cron, null);
        ScheduleManager.startJobs(scheduler);

        //获取订单信息
        MegSocketHandler socketHandler = context.getBean(MegSocketHandler.class);
        socketHandler.initAuthData();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ApplicationContext context =  ContextLoader.getCurrentWebApplicationContext();
        if(context != null) {
            Scheduler scheduler = context.getBean(Scheduler.class);
            //回收线程， 关闭quarzt定时器
            ScheduleManager.shutdowJobs(scheduler);
        }
        ComThreadPool.close();
    }
}

package com.qrcodemall.scheduleTask;

import com.qrcodemall.scheduleTask.MyTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @Author: Peony
 * @Date: 2022/3/29 13:37
 */
@Component
//@Scope("prototype")//每次获取bean时new一个新的
public class DynamicTask {


    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private ScheduledFuture future;
    @Resource(name = "concurrentMap")
    Map<String, ScheduledFuture> concurrentHashMap;


    public void startCron(String cron,String name,MyTask myTask) {
        //String cron = "0/1 * * * * ?";
        System.out.println("start cron "+cron+" "+name+" "+myTask);
        Thread.currentThread().setName(name);
        System.out.println(Thread.currentThread().getName());
        ScheduledFuture f = threadPoolTaskScheduler.schedule(myTask, new CronTrigger(cron));
        concurrentHashMap.put(name, f);
        System.out.println("name = "+name+" started "+" map.size() = "+concurrentHashMap.size());
    }
    public void stop(String name) {
        // 提前测试用来测试线程1进行对比是否关闭。
        ScheduledFuture scheduledFuture = concurrentHashMap.get(name);
        scheduledFuture.cancel(true);
        // 查看任务是否在正常执行之前结束,正常返回true
        boolean cancelled = scheduledFuture.isCancelled();
        while (!cancelled) {
            scheduledFuture.cancel(true);
        }
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
        concurrentHashMap.remove(name);
        System.out.println("name = "+name+" stoped "+" map.size() = "+concurrentHashMap.size());
    }

    public String generateCron(String date) {
        // "2022-3-29 14:40:00"
        StringBuilder sb = new StringBuilder();
        String[] a = date.split(" ");
        String[] b = a[0].split("-");
        String[] c = a[1].split(":");
        sb.append(c[2]).append(" ").append(c[1]).append(" ").append(c[0]).append(" ").
                append(b[2]).append(" ").append(b[1]).append(" ?");
        return sb.toString();
    }
}


package com.whs.basecodedemo.multiThread.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {

    public static List<ScheduledFuture> scheduledFutureList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        //定时任务线程池
        /*long start = System.currentTimeMillis();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(() -> {
            long end = System.currentTimeMillis();
            System.out.println("--------------延时任务执行-----------time:"+(end-start));
        }, 30, TimeUnit.SECONDS);
        Thread.sleep(20000);
        scheduledExecutorService.shutdown();*/

        long start = System.currentTimeMillis();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture future1 = scheduledExecutorService.schedule(() -> {
            long end = System.currentTimeMillis();
            System.out.println("--------------延时任务执行1-----------time:" + (end - start));
        }, 5, TimeUnit.SECONDS);
        ScheduledFuture future2 = scheduledExecutorService.schedule(() -> {
            long end = System.currentTimeMillis();
            System.out.println("--------------延时任务执行2-----------time:" + (end - start));
        }, 7, TimeUnit.SECONDS);
        scheduledFutureList.add(future1);
        scheduledFutureList.add(future2);
        Thread.sleep(3000);
        scheduledFutureList.forEach(future -> {
            future.cancel(true);
        });
        scheduledExecutorService.shutdown();

    }

}

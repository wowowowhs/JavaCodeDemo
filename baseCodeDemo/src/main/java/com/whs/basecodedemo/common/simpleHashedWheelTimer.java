package com.whs.basecodedemo.common;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;

import java.util.concurrent.TimeUnit;

// 参考：https://segmentfault.com/a/1190000042266790
public class simpleHashedWheelTimer {

    public static void main(String[] args) throws InterruptedException {
        // 例子1：5秒后执行TimerTask
        /*
        HashedWheelTimer timer = new HashedWheelTimer(1, TimeUnit.SECONDS, 8);
        // add a new timeout
        timer.newTimeout(timeout -> {
            System.out.println("定时任务运行.......");
        }, 5, TimeUnit.SECONDS);
        */

        // 例子2：任务失效后cancel并让它重新在3秒后执行。
        HashedWheelTimer timer = new HashedWheelTimer(1, TimeUnit.SECONDS, 8);
        Thread.sleep(5000);
        // add a new timeout
        Timeout tm = timer.newTimeout(timeout -> {
            System.out.println("定时任务2运行.......");
        }, 5, TimeUnit.SECONDS);

        // cancel
        if (!tm.isExpired()) {
            System.out.println("取消定时任务2......");
            tm.cancel();
        }

        // reschedule
        timer.newTimeout(tm.task(), 3, TimeUnit.SECONDS);
    }

}

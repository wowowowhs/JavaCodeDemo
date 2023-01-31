package com.whs.basecodedemo.multiThread.threaduse;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable mc = new MyCallable();
        FutureTask<Integer> ft = new FutureTask<>(mc);
        Thread thread = new Thread(ft);
        thread.start();
        System.out.println(ft.get());
    }

}

/**
 * 实现callable接口
 */
class MyCallable implements Callable<Integer> {
    public Integer call() {
        System.out.println("callable。。。。。。");
        return 123;
    }
}

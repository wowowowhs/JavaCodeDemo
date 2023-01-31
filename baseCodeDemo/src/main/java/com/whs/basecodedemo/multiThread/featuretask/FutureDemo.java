package com.whs.basecodedemo.multiThread.featuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * feature使用示例
 */
public class FutureDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future future = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Long start = System.currentTimeMillis();
                while (true) {
                    Long current = System.currentTimeMillis();
                    System.out.println("not arrive...");
                    if ((current - start) > 1000) {
                        System.out.println("arrive...");
                        return 1;
                    }
                }
            }
        });

        try {
            Integer result = (Integer)future.get();
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
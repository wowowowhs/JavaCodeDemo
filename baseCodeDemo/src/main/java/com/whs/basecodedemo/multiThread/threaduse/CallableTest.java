package com.whs.basecodedemo.multiThread.threaduse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallableTest {

    public static final int DATA_SIZE = 1000000;
    public static final int BATCH_SIZE = 10000;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        MyCallable mc = new MyCallable();
        FutureTask<Integer> ft = new FutureTask<>(mc);
        Thread thread = new Thread(ft);
        thread.start();
        System.out.println("myCallable result : " + ft.get());

        //单线程
        System.out.println("====================单线程=======================");
        List<Integer> ids = new ArrayList<>(DATA_SIZE);
        for (int i = 0; i < DATA_SIZE; i++) {
            ids.add(i);
        }
        ReturnObjectCallable returnObjectCallableSingle = new ReturnObjectCallable(ids);
        FutureTask<List<CallableObj>> futureTask = new FutureTask<>(returnObjectCallableSingle);
        Thread singleThread = new Thread(futureTask);
        long singleStartTime = System.currentTimeMillis();
        singleThread.start();
//        System.out.println("result:" + futureTask.get());
        while (true) {
            if (!singleThread.isAlive()) {
                long singleEndTime = System.currentTimeMillis();
                System.out.println("单线程执行时间：" + (singleEndTime - singleStartTime));
                break;
            }
        }
        System.out.println("====================多线程方法1=======================");
        //多线程：并发执行（写法1）
        //创建固定大小的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        int fromIndex = 0;
        int toIndex = 0;
        int total = ids.size();
        int size = 10;
        List<ReturnObjectCallable> returnObjectCallables = new ArrayList<>();
        while (toIndex < total) {
            toIndex = Math.min(fromIndex + size, total);
            List<Integer> idsTemp = ids.subList(fromIndex, toIndex);
            returnObjectCallables.add(new ReturnObjectCallable(idsTemp));
            fromIndex = toIndex;
        }
        long startTime = System.currentTimeMillis();
        //保存线程执行结果
        List<Future<List<CallableObj>>> resultFutures = new ArrayList<>();
        for (ReturnObjectCallable returnObjectCallable : returnObjectCallables) {
            Future<List<CallableObj>> future = threadPool.submit(returnObjectCallable);
            resultFutures.add(future);
        }
        threadPool.shutdown();
        while (true) {
            if (threadPool.isTerminated()) {
                long endTime = System.currentTimeMillis();
                System.out.println("（方法1）多线程并发执行时间：" + (endTime - startTime));
                List<CallableObj> callableObjList = new ArrayList<>();
                for (Future<List<CallableObj>> resultFuture : resultFutures) {
                    callableObjList.addAll(resultFuture.get());
                }
//                System.out.println("（方法1）多线程并发执行结果 : " + callableObjList);
                break;
            }
        }

        /*====================================================*/
        System.out.println("====================多线程方法2=======================");
        Thread.sleep(5000);
        //多线程：并发执行（写法2）
        ExecutorService threadPoolWay2 = Executors.newFixedThreadPool(10);
        fromIndex = 0;
        toIndex = 0;
        total = ids.size();
        //保存线程执行结果
        List<Future<List<CallableObj>>> resultFutures4Way2 = new ArrayList<>();

        while (toIndex < total) {
            toIndex = Math.min(fromIndex + BATCH_SIZE, total);
            List<Integer> idsTemp = ids.subList(fromIndex, toIndex);
            Future<List<CallableObj>> threadRes = threadPoolWay2.submit(new Callable<List<CallableObj>>() {
                @Override
                public List<CallableObj> call() throws Exception {
                    String nameFormat = "ojb-%d";
                    List<CallableObj> res = new ArrayList<>(idsTemp.size());
                    for (Integer id : idsTemp) {
                        String name = String.format(nameFormat, id);
                        CallableObj callableObj = CallableObj.builder()
                                .id(id)
                                .name(name)
                                .build();
                        res.add(callableObj);
                    }
                    return res;
                }
            });
            resultFutures4Way2.add(threadRes);
            fromIndex = toIndex;
        }
        long startTimeWay2 = System.currentTimeMillis();
        threadPoolWay2.shutdown();
        while (true) {
            if (threadPoolWay2.isTerminated()) {
                long endTimeWay2 = System.currentTimeMillis();
                System.out.println("（方法2）多线程并发执行时间：" + (endTimeWay2 - startTimeWay2));
                List<CallableObj> callableObjList = new ArrayList<>();
                for (Future<List<CallableObj>> resultFuture : resultFutures4Way2) {
                    callableObjList.addAll(resultFuture.get());
                }
//                System.out.println("（方法2）多线程并发执行结果 : " + callableObjList);
                break;
            }
        }

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class ReturnObjectCallable implements Callable<List<CallableObj>> {

    private List<Integer> ids;

    @Override
    public List<CallableObj> call() throws Exception {
        String nameFormat = "ojb-%d";
        List<CallableObj> res = new ArrayList<>(ids.size());
        for (Integer id : ids) {
            String name = String.format(nameFormat, id);
//            System.out.println("ThreadName:" + Thread.currentThread().getName() + " name:" + name);
//            Thread.sleep(10);
            CallableObj callableObj = CallableObj.builder()
                    .id(id)
                    .name(name)
                    .build();
            res.add(callableObj);
        }
        return res;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class CallableObj {
    Integer id;
    String name;
}

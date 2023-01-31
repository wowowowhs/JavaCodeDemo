package com.whs.basecodedemo.multiThread.threaduse;

public class RunnableTest {

    public static void main(String[] args) {
        MyRunnable instance = new MyRunnable();
        Thread thread = new Thread(instance);
        thread.start();
    }

}

/**
 * 实现runnable接口
 */
class MyRunnable implements Runnable {
    public void run() {
        System.out.println("runnable。。。。。。");
    }
}

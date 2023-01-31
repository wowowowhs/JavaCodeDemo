package com.whs.basecodedemo.multiThread.threaduse;

public class ThreadTests {

    public static void main(String[] args) {
        MyThread mt = new MyThread();
        mt.start();
    }
}

class MyThread extends Thread {
    public void run() {
        System.out.println("extend thread......");
    }
}

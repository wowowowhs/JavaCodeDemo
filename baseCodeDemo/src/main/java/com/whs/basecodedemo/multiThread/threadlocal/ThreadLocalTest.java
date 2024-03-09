package com.whs.basecodedemo.multiThread.threadlocal;

import java.util.Random;

public class ThreadLocalTest implements Runnable {

    ThreadLocal<Student> studentThreadLocal = new ThreadLocal<>();
    ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

    /**
     * ThreadLocal原理：
     * 1、每个线程有个ThreadLocalMap对象，是个map；
     * 2、该map的key是ThreadLocal，value就是我们定义的对象的值。
     *    eg：在本次代码中，线程A就有个ThreadLocalMap，map中有两个元素，key分别是studentThreadLocal和stringThreadLocal。
     *        值分别是studen和age-test。
     *        当我们通过studentThreadLocal.get()去获取值时，流程如下：
     *        1、获取当前线程的ThreadLocalMap对象
     *        2、studentThreadLocal作为key，从map中取值。
     *    stringThreadLocal同理。
     * 根据上述流程，可以通过threadLocal实现数据线程隔离
     */
    @Override
    public void run() {
        String currentThreadName = Thread.currentThread().getName();
        System.out.println(currentThreadName + " is running...");
        Random random = new Random();
        int age = random.nextInt(100);
        System.out.println(currentThreadName + " is set age: " + age);
        Student Student = getStudent(); //通过这个方法，为每个线程都独立的new一个Student对象，每个线程的的Student对象都可以设置不同的值
        Student.setAge(age);
        System.out.println(currentThreadName + " is first get age: " + Student.getAge());

        System.out.println(currentThreadName + " first age :" + stringThreadLocal.get());
        stringThreadLocal.set(age + "-test");
        System.out.println(currentThreadName + " second age :" + stringThreadLocal.get());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(currentThreadName + " is second get age: " + Student.getAge());

    }

    private Student getStudent() {
        Student Student = studentThreadLocal.get();
        if (null == Student) {
            Student = new Student();
            studentThreadLocal.set(Student);
        }
        return Student;
    }

    public static void main(String[] args) {
        ThreadLocalTest t = new ThreadLocalTest();
        Thread t1 = new Thread(t, "Thread A");
        Thread t2 = new Thread(t, "Thread B");
        t1.start();
        t2.start();
    }

}

class Student {
    int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

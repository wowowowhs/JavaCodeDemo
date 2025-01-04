package com.whs.basecodedemo.proxy.cglib;

public class Calculator {
    public int add(int i, int j) {
        System.out.println("执行原始add方法");
        return i + j;
    }
}

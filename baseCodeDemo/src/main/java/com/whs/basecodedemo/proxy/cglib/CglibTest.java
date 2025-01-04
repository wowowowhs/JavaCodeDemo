package com.whs.basecodedemo.proxy.cglib;

import org.springframework.cglib.core.DefaultNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;

public class CglibTest {

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
        // 设置要代理的目标类
        enhancer.setSuperclass(Calculator.class);
        // 设置拦截器（MethodInterceptor）
        enhancer.setCallback(new LoggingInterceptor());
        // 可选：设置命名策略，避免代理类名称冲突
        enhancer.setNamingPolicy(DefaultNamingPolicy.INSTANCE);
        // 创建并获取代理对象
        Calculator calculatorProxy = (Calculator) enhancer.create();

        // 通过代理对象调用方法
        int sum = calculatorProxy.add(3, 5);
        System.out.println("Sum is: " + sum);
    }

}

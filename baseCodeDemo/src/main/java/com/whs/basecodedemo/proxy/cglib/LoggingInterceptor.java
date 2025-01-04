package com.whs.basecodedemo.proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LoggingInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // 在方法调用前打印日志
        System.out.println("Before calling " + method.getName() + " with arguments: " + Arrays.toString(args));

        // 调用实际方法并获取结果
        Object result = proxy.invokeSuper(obj, args);

        // 在方法调用后打印日志
        System.out.println("After calling " + method.getName() + ", result is: " + result);

        return result;
    }

}

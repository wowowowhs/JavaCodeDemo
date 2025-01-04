package com.whs.basecodedemo.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
// 参考：https://www.cnblogs.com/GilbertDu/p/18245948
public class JdkProxyTest {

    // jkd动态代理，会代理实现接口a的类，并且对于接口a的方法，都会被“套一层”实现
    public static void main(String[] args) {
        // 实例化真实对象
        Sellable realEstate = new RealEstate();

        // 实现代理方式1：单独实现java.lang.reflect.InvocationHandler接口，并在创建代理对象时传入
        // 创建代理对象，并将真实对象传给InvocationHandler
        // 这块代码是动态代理的精髓
        Sellable proxy = (Sellable) Proxy.newProxyInstance(
                Sellable.class.getClassLoader(),
                new Class<?>[]{Sellable.class},
                new LoggingInvocationHandler(realEstate)
        );

        // 现在调用的是代理对象的方法，但会触发InvocationHandler的逻辑
        proxy.sell("豪华别墅");
        proxy.buy("大平层");

        // 输出：
        // 开始销售房源操作...
        // 实际销售房源: 豪华别墅
        // 完成销售房源操作.
        System.out.println("==============================");
        // 实现代理对象2：匿名类实现java.lang.reflect.InvocationHandler接口
        // 参考：https://segmentfault.com/a/1190000039303463。通过lambda表达式实现，也需要一个接口的实现类
        Sellable realEstate4Lambda = new RealEstate();
        Sellable proxyTwo = (Sellable) Proxy.newProxyInstance(Sellable.class.getClassLoader(),
                new Class[]{Sellable.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        // 匿名类实现的jdk动态代理
                        System.out.println("匿名内部类调用方法前start");
                        Object result = method.invoke(realEstate4Lambda, args);
                        System.out.println("匿名内部类调用方法后end");
                        return result;
                    }
                });
        proxyTwo.buy("3房1厅");
        proxyTwo.sell("2房1厅");

        // 输出
//        匿名内部类调用方法前start
//        实际购买房源: 3房1厅
//        匿名内部类调用方法后end
//        匿名内部类调用方法前start
//        实际销售房源: 2房1厅
//        匿名内部类调用方法后end

    }

}

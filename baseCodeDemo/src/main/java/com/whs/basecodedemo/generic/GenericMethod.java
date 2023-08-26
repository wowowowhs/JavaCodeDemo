package com.whs.basecodedemo.generic;

/**
 * 泛化方法
 */
public class GenericMethod {

    public static <T> int print(T t) {
        System.out.println("print :" + t);
        return 0;
    }

    public static <T1> T1 changeData(T1 t1) {
        System.out.println("changeData : " + t1);
        return t1;
    }

    public static void main(String[] args) {
        int res = print("GenericMethod");
        System.out.println("res : " + res);

        String resStr = changeData("changeTest");
        System.out.println(resStr);

    }

}

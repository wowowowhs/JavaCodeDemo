package com.whs.basecodedemo.generic;

import java.util.ArrayList;

public class GenericDemo {

    public static void main(String[] args) {
//        ArrayList<Integer> list = new ArrayList<Integer>();
//        list.add(1);  //这样调用 add 方法只能存储整形，因为泛型类型的实例为 Integer
//        list.getClass().getMethod("add", Object.class).invoke(list, "asd");
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }

        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("abc");
        ArrayList<Integer> list2 = new ArrayList<Integer>();
        list2.add(123);
        System.out.println(list1.getClass() == list2.getClass());


    }


}

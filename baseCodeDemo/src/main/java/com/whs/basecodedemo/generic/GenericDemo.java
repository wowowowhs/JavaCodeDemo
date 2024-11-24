package com.whs.basecodedemo.generic;

import java.util.ArrayList;
import java.util.List;

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

    //参考：https://blog.csdn.net/weixin_49114503/article/details/140770137
    //泛型方法
    /**
     *
     * @param t 传入泛型的参数
     * @param <T> 泛型的类型
     * @return T 返回值为T类型
     * 说明：
     *   1）public 与 返回值中间<T>非常重要，可以理解为声明此方法为泛型方法。
     *   2）只有声明了<T>的方法才是泛型方法，泛型类中的使用了泛型的成员方法并不是泛型方法。
     *   3）<T>表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T。
     *   4）与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E等形式的参数常用于表示泛型。
     */
    public <T> T genercMethod(T t) {
        System.out.println(t.getClass());
        System.out.println(t);
        return t;
    }

    //定义一个泛型方法，不返回内容
    public <T> void print(T t) {
        System.out.println(t);
    }

    //定义一个泛型方法，传入多个泛型
    public <T, F> void query(T t, List<F> f) {
        System.out.println(t);
    }

    //传入T, F  返回T
    public <T, F> T query1(T t, F f) {
        return t;
    }

    //静态的泛型方法 需要在static后用<>声明泛型类型参数
    public static <E> void swap(E[] array, int i, int j) {
        E t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

}

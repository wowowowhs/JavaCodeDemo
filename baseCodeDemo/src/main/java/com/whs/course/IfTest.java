package com.whs.course;

import java.util.Scanner;

public class IfTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a;
        //获取输入，并赋值给a
        a = sc.nextInt();
        //将a与0进行比较
        if (a < 0) {
            //说明a是负数，则取a的相反数
            a = -a;
        }
        System.out.println("a的绝对值："+a);
    }

}

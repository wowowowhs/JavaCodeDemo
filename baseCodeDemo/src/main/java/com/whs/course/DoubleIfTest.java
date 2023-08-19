package com.whs.course;

import java.util.Scanner;

public class DoubleIfTest {
    public static void main(String[] args) {
        int a,b;
        Scanner sc = new Scanner(System.in);
        while(true) {
            //获取a、b的输入
            a = sc.nextInt();
            b = sc.nextInt();
            System.out.println("a=" + a + ",b=" + b);
            //将a、b进行比较
            if (a > b) {
                System.out.println("result a = " + a);
            } else {
                System.out.println("result b = " + b);
            }
        }
    }
}

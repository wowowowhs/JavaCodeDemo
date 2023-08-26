package com.whs.basecodedemo.annotation;

public class AnnotationDemo {

    /**
     * 注解@Reteniton保留时间测试，编译后看字节码文件，如果@Reteniton=RESOURCE,编译后的文件该注解不存在
     *
     * @param args
     */
    public static void main(String[] args) {
        MyAnnotaionClass myAnnotaionClass = new MyAnnotaionClass();
        myAnnotaionClass.print();
    }

}

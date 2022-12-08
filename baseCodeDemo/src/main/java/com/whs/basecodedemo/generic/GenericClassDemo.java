package com.whs.basecodedemo.generic;

/**
 * 泛型类
 */
public class GenericClassDemo {
    public static void main(String[] args) {
        GenericClass<String> genericClass = new GenericClass<String>();
        genericClass.setValue("test...");
        System.out.println(genericClass.getValue());
    }
}

class GenericClass<T> {
    private T value;
    public GenericClass() {
    }
    public GenericClass(T value) {
        this.value = value;
    }
    public T getValue() {
        return value;
    }
    public void setValue(T value) {
        this.value = value;
    }
}

package com.whs.basecodedemo.generic;

/**
 * 泛化接口
 */
public class GenericInterfaceImpl implements GenericInterface<String> {
    @Override
    public void print(String value) {
        System.out.println("print : " + value);
    }
}


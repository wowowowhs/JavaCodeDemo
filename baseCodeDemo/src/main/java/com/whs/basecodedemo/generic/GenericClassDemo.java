package com.whs.basecodedemo.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 泛型类
 */
public class GenericClassDemo {
    public static void main(String[] args) {
        GenericClass<String> genericClass = new GenericClass<String>();
        genericClass.setValue("test...");
        System.out.println(genericClass.getValue());

        GenericClassTemp<String, Integer> genericClassTemp = new GenericClassTemp<>();
        genericClassTemp.setValue1("hello");
        genericClassTemp.setValue2(1);
        System.out.println("genericClassTemp:" + genericClassTemp);
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

//参考：https://www.cnblogs.com/jpfss/p/9928747.html
@Data
@AllArgsConstructor
@NoArgsConstructor
class GenericClassTemp<TYPE1, TYPE2> {
    private TYPE1 value1;
    private TYPE2 value2;
}

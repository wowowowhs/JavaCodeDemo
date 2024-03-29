package com.whs.basecodedemo.spi.javaspi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 参考：https://zhuanlan.zhihu.com/p/28909673
 */
public class JavaSpiTest {

    public static void main(String[] args) {
        ServiceLoader<Search> serviceLoader = ServiceLoader.load(Search.class);
        Iterator<Search> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Search search = iterator.next();
            search.searchDoc("hello world");
        }
    }

}

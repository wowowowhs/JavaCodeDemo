package com.whs.basecodedemo.proxy.jdk;

public class RealEstate implements Sellable{
    @Override
    public void sell(String item) {
        System.out.println("实际销售房源: " + item);
    }

    @Override
    public void buy(String item) {
        System.out.println("实际购买房源: " + item);
    }

    public void mySell(String str){
        System.out.println("RealEstate 自己的方法，执行该方法时，不会被代理");
    }
}

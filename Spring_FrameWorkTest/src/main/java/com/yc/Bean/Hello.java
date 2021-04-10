package com.yc.Bean;

import com.yc.MyFrameWork.MyAnnoationConfig.MyAutowired;
import com.yc.MyFrameWork.MyAnnoationConfig.MyComponent;
import com.yc.MyFrameWork.MyAnnoationConfig.MyPostConstruct;
import com.yc.MyFrameWork.MyAnnoationConfig.MyResource;

@MyComponent
public class Hello {

    private Address address;

    //@MyResource(name = "address")
    @MyAutowired
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Hello{" +
                "address=" + address.toString() +
                '}';
    }

    public Address getAddress() {
        return address;
    }

    public Hello() {
        System.out.println("这是无参数的构造方法");
    }

    @MyPostConstruct
    public void PostConstruct(){
        System.out.println("在构造方法后执行");
    }

    public void show(){
        System.out.println("这是show方法");
    }
}

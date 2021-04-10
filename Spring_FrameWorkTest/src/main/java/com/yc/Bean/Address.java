package com.yc.Bean;

import com.yc.MyFrameWork.MyAnnoationConfig.MyComponent;
import com.yc.MyFrameWork.MyAnnoationConfig.MyValue;

@MyComponent
public class Address {
    private String name;

    public Address(){
        System.out.println("address的无参构造");
    }
    public String getName() {
        return name;
    }

    @MyValue(value = "SHANGHAI")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                '}';
    }
}

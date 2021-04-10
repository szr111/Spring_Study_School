package com.yc.Bean;

import com.yc.SpringFrameWrok.stereotype.MyComponent;

@MyComponent
public class Person {
    private String name="SHANGHAI";

    public Person(){
        System.out.println("这是person 的构造");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

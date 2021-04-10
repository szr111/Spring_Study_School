package com.yc.Bean;

import com.yc.SpringFrameWrok.stereotype.*;

@MyComponent
public class Hello {
    private Person person;

    public Person getPerson() {
        return person;
    }

    @MyResource(name = "person")
    public void setPerson(Person person) {
        this.person = person;
    }

    @MyPostConstruct
    public void setup(){
        System.out.println("MyPostConstruct");
    }
    @MyPreDestroy
    public void destroy(){
        System.out.println("MyPreDestroy");
    }

    public Hello(){
        System.out.println("hello 构造");
    }

    public void show(){
        System.out.println("show");
    }


}

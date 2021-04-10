package com.yc.Biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

@Component(value = "m1")
/*@Controller*/
/*@Service*/
public class HelloWorld {

    private String name;
    private Address address;

    public HelloWorld() {
        System.out.println("无参构造");
    }

    public String getName() {
        return name;
    }

    @PreDestroy
    public void ss(){
        System.out.println("这是PreDestroy");
    }
    @Value(value = "xiaom")
    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }


   /* @Autowired
    @Qualifier(value = "a1")
    @Resource(name = "a1")*/
    @Inject
    @Named(value = "a1")
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "HelloWorld{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }

    public void Hello(){
        System.out.println("hello world");
    }
}

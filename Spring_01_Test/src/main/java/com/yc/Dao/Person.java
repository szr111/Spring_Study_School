package com.yc.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service(value = "p1")
public class Person {
    @Value(value = "xiaoli")
    private String name;
   @Value(value = "12")
    private int id;
    @Autowired
    private Address address;

    public Person() {
    }

    public Person(String name, int id, Address address) {
        this.name = name;
        this.id = id;
        this.address = address;
    }
/*public Person(String name, int id, Address address) {
        this.name = name;
        this.id = id;
        this.address = address;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", address=" + address.toString() +
                '}';
    }
}

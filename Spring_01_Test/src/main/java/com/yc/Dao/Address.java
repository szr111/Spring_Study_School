package com.yc.Dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Address {
    private String name;

    public String getName() {
        return name;
    }

    @Value(value = "SHANGHAI")
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

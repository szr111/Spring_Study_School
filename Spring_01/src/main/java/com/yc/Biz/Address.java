package com.yc.Biz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "a1")
public class Address {

    @Value(value = "shanghai")
    private String address;

   /*

    public void setAddress(String address) {
        this.address = address;
    }*/

    public String getAddress() {
        return address;
    }
    @Override
    public String toString() {
        return "Address{" +
                "address='" + address + '\'' +
                '}';
    }
}

package com.yc.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Random;

@Component(value = "m1")
public class MysqlUserMapperImp implements UserMapper{

    @Override
    public void adduser() {

        System.out.println("Mysql增加了一个用户");
    }

    @Override
    public void update() {
        System.out.println("mysql中修改了一个用户");
    }

    @Override
    public void finduser() {
        Random rd=new Random();
        try {
            Thread.sleep(rd.nextInt(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("myslq中找到了某一个用户");
    }
}

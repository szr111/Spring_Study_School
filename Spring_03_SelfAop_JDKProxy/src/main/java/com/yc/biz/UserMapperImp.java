package com.yc.biz;

public class UserMapperImp implements UserMapper{
    @Override
    public void add() {
        System.out.println("增加了一个用户");
    }

    @Override
    public int fint(String name) {

        System.out.println("找了名字叫:"+name+"-的用户");
        return 100;
    }
}

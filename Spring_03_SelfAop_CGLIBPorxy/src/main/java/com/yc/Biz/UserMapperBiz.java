package com.yc.Biz;

public class UserMapperBiz {
    public void add() {
        System.out.println("增加了一个用户");
    }

    public int fint(String name) {

        System.out.println("找到了名字叫:"+name+"-的用户");
        return 100;
    }
}

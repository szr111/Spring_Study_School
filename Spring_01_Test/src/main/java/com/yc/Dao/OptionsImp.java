package com.yc.Dao;

public class OptionsImp implements OptionsMapper{

    @Override
    public void add() {
        System.out.println("add方法");
    }

    @Override
    public void del() {
        System.out.println("del方法");
    }

    @Override
    public void query() {
        System.out.println("query方法");
    }

    @Override
    public void update() {
        System.out.println("update方法");
    }

}

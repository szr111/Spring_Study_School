package com.yc.biz;

import com.yc.dao.MysqlUserMapperImp;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component(value ="u")
public class ub implements ubMpper{
    @Resource(name = "m1")
    MysqlUserMapperImp userMapperImp;

    @Override
    public void add() {
        userMapperImp.adduser();
    }

    @Override
    public void fint() {
        userMapperImp.finduser();
    }

    @Override
    public void upd() {
        userMapperImp.update();
    }
   /*
    }

    public void setUserMapperImp(MysqlUserMapperImp userMapperImp) {
        this.userMapperImp = userMapperImp;
    }
    @Override
    public void add(){

    }
    @Override
    public void upd(){
        userMapperImp.update();
    }
    @Override
    public void find(){
        userMapperImp.finduser();
    }*/

}

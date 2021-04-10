package com.yc.biz;

import com.yc.Myconfig;
import com.yc.dao.UserMapper;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Myconfig.class)
public class ubTest  {

    @Autowired
    private ubMpper ubb;

    @Test
    public void testAdd() {
        ubb.add();
    }

    @Test
    public void testUpd() {
        ubb.upd();
    }

    @Test
    public void testFind() {
    ubb.fint();
    }
}
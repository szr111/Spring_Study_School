package com.yc.MyConfig;

import com.yc.Dao.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.yc")
public class Myconfig {

    //@Bean
    /*public Person p12(){
        return new Person();
    }*/

}

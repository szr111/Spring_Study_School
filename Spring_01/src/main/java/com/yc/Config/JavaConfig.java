package com.yc.Config;

import com.yc.Biz.HelloWorld;
import com.yc.Biz.ss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan("com.yc.Biz")
public class JavaConfig {

    @Bean
    public HelloWorld h1(){
        return new HelloWorld();
    }
    @Bean
    public ss s1(){
        return new ss();
    }

}

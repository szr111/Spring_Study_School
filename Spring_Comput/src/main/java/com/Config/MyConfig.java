package com.Config;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Random;

@CacheConfig
@ComponentScan("com")
public class MyConfig {
    @Bean
    public Random r1(){
       return new Random();
    }

}

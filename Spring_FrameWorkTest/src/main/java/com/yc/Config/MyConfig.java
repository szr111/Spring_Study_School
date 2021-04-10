package com.yc.Config;

import com.yc.Bean.Hello;
import com.yc.MyFrameWork.MyAnnoationConfig.MyBean;
import com.yc.MyFrameWork.MyAnnoationConfig.MyComponentScan;
import com.yc.MyFrameWork.MyAnnoationConfig.MyConfiguration;

@MyConfiguration
@MyComponentScan(resourcePattern="com.yc.Bean")
public class MyConfig {

    /*@MyBean
    public Hello h1(){
        return  new Hello();
    }*/
   /* @MyBean
    public Hello h2(){
        return  new Hello();
    }*/

}

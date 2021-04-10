package com.yc.Config;

import com.yc.Bean.Hello;
import com.yc.SpringFrameWrok.stereotype.MyBean;
import com.yc.SpringFrameWrok.stereotype.MyComponentScan;
import com.yc.SpringFrameWrok.stereotype.MyConfiguration;

@MyConfiguration
@MyComponentScan(resourcePattern = {"com.yc.Bean"})
public class MyConfig {

    /*@MyBean
    public Hello h1(){
        return new Hello();
    }*/
}

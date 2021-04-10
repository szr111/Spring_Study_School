package com.yc.MyFrameWork.MyAnnoationConfig;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface MyComponentScan {
    String[] resourcePattern() default {};
}

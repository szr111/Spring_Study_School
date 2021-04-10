package com.yc.SpringFrameWrok.stereotype;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface MyComponentScan {
    String[] resourcePattern() default {};
}

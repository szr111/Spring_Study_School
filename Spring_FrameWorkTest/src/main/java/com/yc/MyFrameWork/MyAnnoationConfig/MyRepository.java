package com.yc.MyFrameWork.MyAnnoationConfig;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MyRepository
public @interface MyRepository {
    String value() default "";
}

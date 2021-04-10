package com.yc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(value = 100)
public class Aspect3UserMapper {

    @Around("execution(* com.yc.biz.ub.f*(..))")
    public void computtime(ProceedingJoinPoint pj) throws Throwable {
        System.out.println("进入环绕3···");
        Object o = pj.proceed();
        System.out.println("环绕结束3···");

    }
}

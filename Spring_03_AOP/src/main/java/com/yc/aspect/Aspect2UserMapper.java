package com.yc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Order(10)
@Component
public class Aspect2UserMapper {
    @Pointcut("execution(* com.yc.biz.ub.add*(..))")
    private void a(){}
    @Pointcut("execution(* com.yc.biz.ub.u*(..))")
    private void b(){}
    @Pointcut("execution(* com.yc.biz.ub.f*(..))")
    private void c(){}

    @Before("com.yc.aspect.Aspect2UserMapper.a()")
    public void log(JoinPoint jp){
        System.out.println("这是前置增强2");


    }
    @After("com.yc.aspect.Aspect2UserMapper.b()")
    public void saybye(JoinPoint jp){
        System.out.println("bye-----bye----2-");

    }

    @Around("com.yc.aspect.Aspect2UserMapper.c()")
    public void computtime(ProceedingJoinPoint pj) throws Throwable {
        System.out.println("进入环绕2···");
        Object o = pj.proceed();
        System.out.println("环绕结束2···");

    }
    /*@Around("com.yc.aspect.Aspect2UserMapper.c()")
    public void computtime2(ProceedingJoinPoint pj) throws Throwable {
        System.out.println("进入环绕22···");
        Object o = pj.proceed();
        System.out.println("环绕结束22···");
    }*/

}

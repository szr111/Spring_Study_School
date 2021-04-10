package com.yc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component(value = "a1")
@Order(1)
public class AspectUserMapper {
    @Pointcut("execution(* com.yc.biz.ub.add*(..))")
    private void a(){}

    @Pointcut("execution(* com.yc.biz.ub.u*(..))")
    private void b(){}
    @Pointcut("execution(* com.yc.biz.ub.f*(..))")
    private void c(){}

    @Before("com.yc.aspect.AspectUserMapper.a()||com.yc.aspect.AspectUserMapper.b()")
    public void log(JoinPoint jp){
        System.out.println("这是前置增强1");
        Date date = new Date();
        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间为："+s.format(date));
      /*  Object o = jp.getTarget();
        System.out.println("目标对象是"+o);
        System.out.println(jp.getSignature());*/

    }


    @After("com.yc.aspect.AspectUserMapper.a()||com.yc.aspect.AspectUserMapper.b()")
    public void saybye(JoinPoint jp){
        System.out.println("bye-----bye-----");
        System.out.println(jp.getTarget());
        System.out.println(jp.getStaticPart());
        System.out.println(jp.getSignature());
    }

   /* @Around("com.yc.aspect.AspectUserMapper.c()")
    public void computtime(ProceedingJoinPoint pj) throws Throwable {
        System.out.println("进入环绕···");
        Object o = pj.proceed();
        System.out.println("环绕结束···");

    }*/

}

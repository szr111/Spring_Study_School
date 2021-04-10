package com.yc.handle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelfJDKAspect implements InvocationHandler {
    private Object target;

    public SelfJDKAspect(Object target) {
        this.target = target;
    }

    public Object createProxy(){
        return Proxy.newProxyInstance(this.target.getClass().getClassLoader(),this.target.getClass().getInterfaces(),this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("---proxy代表的是"+proxy.getClass());
        System.out.println("---method代表的是"+method);
        System.out.println("---args代表的是"+args);

        log();
        Object o = method.invoke(this.target, args);
        return o;
    }

    public void log(){
        System.out.println("=======日志增强=========");
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        System.out.println("现在的时间是:"+format);
    }
}

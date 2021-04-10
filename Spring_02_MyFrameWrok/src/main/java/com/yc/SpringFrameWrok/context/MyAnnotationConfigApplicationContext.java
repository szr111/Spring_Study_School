package com.yc.SpringFrameWrok.context;

import com.yc.SpringFrameWrok.stereotype.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class MyAnnotationConfigApplicationContext implements MyApplicationContext{

    Map<String,Object> map=new HashMap<String,Object>();

    public MyAnnotationConfigApplicationContext(Class<?>... componentClasses) throws InstantiationException, IllegalAccessException {
        try {
            register(componentClasses);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void register(Class<?>[] componentClasses) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if(componentClasses==null||componentClasses.length<=0) {
            throw new RuntimeException("没有指定配置类");
        }
        for(Class cl:componentClasses){
            if(!cl.isAnnotationPresent(MyConfiguration.class)){
                continue;
            }
            String [] basePackage=getAppconfigbasePackage(cl);
            if(cl.isAnnotationPresent(MyComponentScan.class)){
                MyComponentScan mcs = (MyComponentScan) cl.getAnnotation(MyComponentScan.class);
               if(mcs.resourcePattern()!=null && mcs.resourcePattern().length>0) {
                   basePackage = mcs.resourcePattern();
               }
            }
            Object obj = cl.newInstance();
            handleAtMyBean(cl,obj);

            System.out.println(basePackage+"----");
            for (String s : basePackage) {
                try {
                    scanPackageAndSubPackageClasses(s);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            handlemanageBean();

            handleDI(map);
        }
    }

    private void handleDI(Map<String, Object> map) throws InvocationTargetException, IllegalAccessException {
        Collection<Object> collection = map.values();
        for (Object o : collection) {
            Class<?> aClass = o.getClass();
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(MyAutowired.class)&&method.getName().startsWith("set")){
                    invokeAutowiredMethod(method,o);
                }else if(method.isAnnotationPresent(MyResource.class)&&method.getName().startsWith("set")){
                    invokerepositryMethod(method,o);
                }
            }


            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                if(field.isAnnotationPresent(MyAutowired.class)){

                }else if(field.isAnnotationPresent(MyResource.class)){

                }
            }



        }
    }

    private void invokerepositryMethod(Method method, Object o) throws InvocationTargetException, IllegalAccessException {
        MyResource annotation = method.getAnnotation(MyResource.class);
        String beanId=annotation.name();
        if(beanId==null||beanId.equalsIgnoreCase("")){
            String name=method.getParameterTypes()[0].getSimpleName();
            beanId=name.substring(0,1).toLowerCase()+name.substring(1);
        }
        Object o1 = map.get(beanId);
        method.invoke(o,o1);
    }

    private void invokeAutowiredMethod(Method method, Object o) throws InvocationTargetException, IllegalAccessException {
        Class<?> typeClass = method.getParameterTypes()[0];
        Set<String> key = map.keySet();
        for (String s : key) {
            Object o1 = map.get(key);

            if(o1.getClass().getName().equalsIgnoreCase(typeClass.getName())){
               method.invoke(o,o1);
            }
        }

    }

    private void handlemanageBean() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for (Class c:managedBeanClasses){
            if(c.isAnnotationPresent(MyComponent.class)){
                saveManage(c);
            }else if(c.isAnnotationPresent(MyService.class)){
                saveManage(c);
            }else if(c.isAnnotationPresent(MyRepository.class)){
                saveManage(c);
            }else if (c.isAnnotationPresent(MyController.class)){
                saveManage(c);
            }
        }
    }

    public void saveManage(Class c) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o = c.newInstance();
        handlePostCunstruct(o,c);
        String beanID = c.getSimpleName().substring(0, 1).toLowerCase() + c.getSimpleName().substring(1);
        map.put(beanID,o);
    }

    public void scanPackageAndSubPackageClasses(String basePackage) throws IOException, ClassNotFoundException {
        String packagePath=basePackage.replaceAll("\\.","/");
        System.out.println("扫描包路径"+basePackage+"===="+"替换后"+packagePath);

        //得到某一个包的加载路径
        Enumeration<URL> files = Thread.currentThread().getContextClassLoader().getResources(packagePath);
        while (files.hasMoreElements()){
            URL url=files.nextElement();
            System.out.println("配置的扫描路径为"+url.getFile());//C:/Users/IdeaProjects/Spring_Study_School/Spring_02_MyFrameWrok/target/classes/com/yc/Bean


            findClassesInPackages(url.getFile(),basePackage);
        }

    }

    /**
     *
     */
    private Set<Class> managedBeanClasses=new HashSet<>();

    /**
     * 查找 file 下面以及子包中所有的要托管的class 存在一个set中
     * @param file
     * @param basePackage
     */
    private void findClassesInPackages(String file, String basePackage) throws ClassNotFoundException {
        File f = new File(file);
        File[] classFiles=f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".class")||file.isDirectory();
            }
        });
        //System.out.println(classFiles);
        for (File cf :classFiles){
            if(cf.isDirectory()){
                basePackage+="."+cf.getName().substring(cf.getName().lastIndexOf("/")+1);
                findClassesInPackages(cf.getAbsolutePath(),basePackage);
            }else{
                URL[] urls=new URL[]{};
                URLClassLoader ucl=new URLClassLoader(urls);
                Class c= ucl.loadClass(basePackage+"."+cf.getName().replace(".class",""));
                managedBeanClasses.add(c);
            }
        }
    }

    private void handleAtMyBean(Class cls,Object obj) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(MyBean.class)){
                Object o = method.invoke(obj, null);
                //TODO 加入处理  MyPostConstruct的方法
                handlePostCunstruct(o,o.getClass());
                map.put(method.getName(),o);//o返回的是激活方法返回的对象h1
                //handleMyPreDestroy(o,o.getClass());
            }
        }
    }

    /**
     * 处理一个bean中的注解MyPreDestroy
     * @param o
     * @param aClass
     */
    private void handleMyPreDestroy(Object o, Class<?> aClass) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(MyPreDestroy.class)){
                Object o1 = method.invoke(o);
            }
        }
    }

    /**
     * 处理一个bean中的MyPostConstruct注解
     * @param o
     * @param aClass
     */
    private void handlePostCunstruct(Object o, Class<?> aClass) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(MyPostConstruct.class)){
                method.invoke(o);
            }
        }

    }

    private String[] getAppconfigbasePackage(Class cl) {
        String [] paths=new String[1];
        paths[0] =cl.getPackage().getName();
        return paths;
    }

    @Override
    public Object getBean(String id) {
        return map.get(id);
    }
}

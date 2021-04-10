package com.yc.MyFrameWork.Context;

import com.yc.MyFrameWork.MyAnnoationConfig.*;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;


public class MyAnnotationConfigApplicationContext implements MyApplicationContext{

    Map<String,Object> map=new HashMap<String,Object>();

    public MyAnnotationConfigApplicationContext(Class<?>... componentClasses){
        try {
            register(componentClasses);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过注解放进来的类都是这个方法操作 进行IOC和DI
     * @param componentClasses
     */
    private void register(Class<?>[] componentClasses) throws IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException {
        //通过构造方法拿到了注解类 那么我们就要先判断是否存在
        if(componentClasses==null || componentClasses.length<=0){
            throw new RuntimeException("请加入注解类...");
        }
        //如果存在注解类，我们设置的是一个数组，那我们就迭代分别分析吧
        for (Class<?> cl : componentClasses) {
            //从上往开始分析这个注解类  先从@Myconfiguration开始 没有的话就直接跳过
            //他就不是一个注解配置类
            if(!cl.isAnnotationPresent(MyConfiguration.class)){
                continue;
            }

            String[] basepackage=null;
            //判断是否存在扫描注解
            if(cl.isAnnotationPresent(MyComponentScan.class)){
                //拿到这个扫描的注解类 那么我们就可以拿到要扫描的路径
                MyComponentScan scan = cl.getAnnotation(MyComponentScan.class);
                //如果扫描注解没有声名路径 那么我们就以他的根路径为扫描
                basepackage= scan.resourcePattern();
                if(scan.resourcePattern().length==0 ){
                   basepackage= getPathByMySelf(cl);
                    System.out.println(basepackage);
                }
                //System.out.println(paths);
            }

            Object o = cl.newInstance();
            //处理注解类中带有@MyBean的注解的方法  直接创建起来
            handleBean(cl,o);

            //scan注解中可能含多个路径  我们循环分别出来
            for (String s : basepackage) {
                findbackageclasspath(s);
            }
        }
            //处理扫描的类
            handleManageBeanClasses();
            //对已经被创建的对象进行di
            handleDI(map);

    }

    /**
     * 对容器的对象进行di操作
     * @param map
     */
    private void handleDI(Map<String, Object> map) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        //取出所有已经创建好的对象
        Collection<Object> values = map.values();
        for (Object value : values) {
            //拿到这个对象的反射类
            Class<?> aClass = value.getClass();
            //根据反射拿到方法
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(MyAutowired.class)&&method.getName().startsWith("set")){
                    //自动注入  通过类型  byType
                    handleMyAutowired(method,value);
                }else if(method.isAnnotationPresent(MyResource.class)&&method.getName().startsWith("set")){
                    //自动注入 通过id  byName方式
                    handleMyResource(method,value);
                }else if(method.isAnnotationPresent(MyValue.class)&&method.getName().startsWith("set")){
                    handleMyValue(method,value);
                }
            }
        }

    }

    /**
     * 注入 string值
     * @param method
     * @param value
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void handleMyValue(Method method, Object value) throws InvocationTargetException, IllegalAccessException {
        MyValue v = method.getAnnotation(MyValue.class);
        String s = v.value();
        method.invoke(value,s);
    }

    /**
     * 通过byType方式自动注入 bean  拿到方法的参数类型名与容器中对象的类型比较
     * @param method
     * @param value
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void handleMyAutowired(Method method, Object value) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Class type = method.getParameterTypes()[0];
        Object ocInstance = type.newInstance();
        //System.out.println(ocInstance);
        for (Object o : map.values()) {
            Class<?> aClass = o.getClass();
            //System.out.println(aClass);
            if(aClass.isInstance(ocInstance)){
               method.invoke(value,o);
            }
        }
    }

    /**
     * 通过byName方式自动注入 bean  拿到注解的name值与容器中对象id比较
     * @param method
     * @param value
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void handleMyResource(Method method, Object value) throws InvocationTargetException, IllegalAccessException {
        MyResource myResource = method.getAnnotation(MyResource.class);
        String name = myResource.name();
        Set<String> keySet = map.keySet();
        if(name==null||name.equals("")){
            Class<?> type = method.getParameterTypes()[0];
            String s = type.getSimpleName().substring(0, 1).toLowerCase() + type.getSimpleName().substring(1);
            for (String m : keySet) {
                if(m.equals(s)){
                    Object oc = map.get(s);
                    method.invoke(value,oc);
                }
            }
        }else{
            for (String s : keySet) {
                if(s.equals(name)){
                    Object oc = map.get(name);
                    method.invoke(value,oc);
                }
            }
        }


    }

    /**
     * 处理前面扫描过滤拿到的类  如果有这些注解  那么我们就吧激活创建 并存在map中去 等待用户去调用这个对象
     */
    private void handleManageBeanClasses() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for (Class managedBeanClass : managedBeanClasses) {
            if(managedBeanClass.isAnnotationPresent(MyComponent.class)){
                //对符合条件的类进行创建 ioc
                handleManageClasses(managedBeanClass);
            }else if(managedBeanClass.isAnnotationPresent(MyService.class)){
                handleManageClasses(managedBeanClass);
            }else if (managedBeanClass.isAnnotationPresent(MyController.class)){
                handleManageClasses(managedBeanClass);
            }else if(managedBeanClass.isAnnotationPresent(MyRepository.class)){
                handleManageClasses(managedBeanClass);
            }
        }
    }

    /**
     *
     * @param managedBeanClass
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void handleManageClasses(Class managedBeanClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        //创建这个类的实例
        Object o = managedBeanClass.newInstance();
        //拿到这个类名 对象的id为这个类的首字母小写
        handlePostConstruct(managedBeanClass);
        String s = managedBeanClass.getSimpleName().substring(0, 1).toLowerCase() + managedBeanClass.getSimpleName().substring(1);
        //将这个类的实例存入map中 id为首字母小写
        map.put(s,o);
    }

    //用来存要给spring托管的类 一会直接一起创建
    private HashSet<Class> managedBeanClasses=new HashSet<Class>();
    /**
     * 根据要扫描包的路径  拿到这个包的要加载磁盘路径 就是target下面的那个路径
     * @param s
     */
    private void findbackageclasspath(String s) throws ClassNotFoundException {//s:com.yc.Bean
        String packagepath = s.replaceAll("\\.", "/");
        System.out.println("替换前："+s+"-----"+"替换后："+packagepath);//packpath:com/yc/Bean

        URL url = this.getClass().getClassLoader().getResource(packagepath);//url获得的是文件名 加路径 要吧他转成磁盘路径
        System.out.println("获取包加载的根路径："+url.getFile());//url.getFile()拿到的是磁盘路径

        //拿到这个包的绝对路径 我们就要根据这个路径去拿他的下加赞类文件或者子包
        getpackageclasspaths(url.getFile(),s);

    }

    /**
     * 通过了url包的路径 我们就可以在在这个路径下查看类文件或子包
     * @param url
     * @param basepackage
     */
    private void getpackageclasspaths(String url,String basepackage) throws ClassNotFoundException {
        //file已经拿到了 url路径下的所有类文件了
        File file = new File(url);
        //System.out.println(file);
        //过滤file下的类文件或目录
        //files拿到了hello.class文件的路径 C:\Users\IdeaProjects\Spring_Study_School\Spring_FrameWorkTest\target\classes\com\yc\Bean\Hello.class
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".class") || pathname.isDirectory();
            }
        });

        //对过滤完后的所有文件进行迭代
        for (File f : files) {

            if(f.isDirectory()){
                //是目录继续递归
                basepackage+="."+f.getName().substring(f.getName().lastIndexOf("/")+1);
                findbackageclasspath(basepackage);
            }else{
                //将磁盘路径下的文件  加载成类
                /*URL[] url1 = new URL[]{};
                URLClassLoader ucl=new URLClassLoader(url1);
                Class c= ucl.loadClass(basepackage+"."+f.getName().replace(".class",""));*/
                Class c = Class.forName(basepackage + "." + f.getName().replace(".class", ""));
                managedBeanClasses.add(c);
            }
        }
       // System.out.println(files);
    }


    /**
     * 处理注解配置类中的@Bean方法
     * @param cl
     */
    private void handleBean(Class<?> cl,Object o) throws InvocationTargetException, IllegalAccessException {
        //对反射类(注解配置类)中的方法
        Method[] methods = cl.getDeclaredMethods();
        for (Method method : methods) {
            if(!method.isAnnotationPresent(MyBean.class)){
                continue;
            }else{
                //激活带着@Bean注解方法  返回的我们应该知道是一个对象
                Object o1 = method.invoke(o);
                //@MyPostConstruct 在构造方法执行后执行
                //拿到这个对象 然后通过反射获得这个类 去类中查看是否有@MyPostConstruct注解 有则激活他
                handlePostConstruct(o1);
                //然后将方法名作为key,得到的对象作为值 存到事先声名好的map集合中
                String name = method.getName();
                map.put(name,o1);
            }

        }
    }

    /**
     * 激活对象中的带有MyPostConstruct注解的方法
     * @param o1
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void handlePostConstruct(Object o1) throws InvocationTargetException, IllegalAccessException {
        //通过对象.getClass拿到这个对象的反射类  获取这个类中的方法 迭代查看
        Method[] methods = o1.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(MyPostConstruct.class)){
                //存在就激活他
                Object o = method.invoke(o1);
            }
        }
    }


    /**
     * 扫描未声名 我们给他的一个扫描路径 为这个类的包名
     * @param cl
     * @return
     */
    private String[] getPathByMySelf(Class<?> cl) {
        String [] paths=new String[1];
        paths[0]=cl.getPackage().getName();
        return paths;
    }

    @Override
    public Object getBean(String id) {
        return map.get(id);
    }
}

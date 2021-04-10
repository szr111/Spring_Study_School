import com.yc.bean.Address;
import com.yc.bean.h;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;

public class MytEST {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        /*URL url = Thread.currentThread().getContextClassLoader().getResource("com/yc/bean");
        System.out.println(url);

        System.out.println(url.getFile());
        File file = new File(url.getFile());
        System.out.println(file.isDirectory());
        System.out.println(file.isFile());
        System.out.println(file.getAbsolutePath());
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".class") || pathname.isDirectory();
            }
        });
        //System.out.println(files.getClass().getName());
        for (File file1 : files) {
            String s = file1.getName().replace(".class", "");
            System.out.println(s);
        }
        Class<?> aClass = Class.forName("com.yc.bean.h");
        Field[] field = aClass.getDeclaredFields();
        for (Field field1 : field) {
            System.out.println(field1.getModifiers()+"\t"+field1.getType()+"\t"+field1.getName());

        }*/
        Address address=new Address();

        Class<? extends Address> addressClass = address.getClass();

        Class<?> aClass = Class.forName("com.yc.bean.h");
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            if(method.getName().startsWith("setA")){
                Class<?> type = method.getParameterTypes()[0];
                Object o =  type.newInstance();
               /* System.out.println(o);
                o.setName("ss");
                System.out.println(o.getName());*/
                /*if(address instanceof )*/
                if(o instanceof Address){
                    System.out.println("ok");
                }
            }
        }
    }
}

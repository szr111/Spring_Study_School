import com.yc.Biz.Address;
import com.yc.Biz.HelloWorld;
import com.yc.Biz.ss;
import com.yc.Config.JavaConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {

    @Test
    public void Test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("Application2.xml");
        HelloWorld h1 = context.getBean("h1", HelloWorld.class);
        String s = h1.toString();
        System.out.println(s);
        h1.Hello();
    }
    @Test
    public void Test2(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        HelloWorld h1 = context.getBean("h1", HelloWorld.class);
        System.out.println(h1.toString());
        h1.Hello();
    }
    @Test
    public void Test3(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        ss s1 = (ss)context.getBean("s1");
        s1.show();
    }
    @Test
    public void Test4(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        Address a1 = context.getBean("a1", Address.class);
        System.out.println(a1.getAddress());
    }
}

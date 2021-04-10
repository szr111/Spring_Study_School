import com.yc.Dao.Person;
import com.yc.MyConfig.Myconfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Myconfig.class)
public class MyTest {

    @Autowired
    @Qualifier(value = "p1")
    Person person;

    @Test
    public void Test1(){
        System.out.println(person.toString());
        /*AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Myconfig.class);
        Person p1 = context.getBean("p1", Person.class);
        System.out.println(p1.toString());*/
    }
    @Test
    public void Test2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        Person pp1 = context.getBean("pp1", Person.class);
        System.out.println(pp1.toString());
    }
}

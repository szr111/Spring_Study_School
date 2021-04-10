import com.yc.Bean.Hello;
import com.yc.Config.MyConfig;
import com.yc.SpringFrameWrok.context.MyAnnotationConfigApplicationContext;
import com.yc.SpringFrameWrok.context.MyApplicationContext;
import org.junit.Test;

public class MyTest {

    @Test
    public void Test1() throws IllegalAccessException, InstantiationException {
        MyApplicationContext context = new MyAnnotationConfigApplicationContext(MyConfig.class);
        Hello hello = (Hello) context.getBean("hello");
        System.out.println(hello.getPerson().getName());
       /* hello.show();
        System.out.println(hello.getPerson());*/
    }
}

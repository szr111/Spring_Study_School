import com.yc.Bean.Address;
import com.yc.Bean.Hello;
import com.yc.Config.MyConfig;
import com.yc.MyFrameWork.Context.MyAnnotationConfigApplicationContext;
import com.yc.MyFrameWork.Context.MyApplicationContext;
import org.junit.Test;

public class MyTest {
    @Test
    public void Test1(){
        MyApplicationContext context = new MyAnnotationConfigApplicationContext(MyConfig.class);
        Hello h1 = (Hello) context.getBean("hello");
        h1.show();
        System.out.println(h1.toString());
        /*Address a1 = (Address) context.getBean("address");
        System.out.println(a1.toString());*/
       /* Hello h12 = (Hello) context.getBean("h1");
        h12.show();*/
        //h1.PostConstruct();
       /* Hello h2 = (Hello) context.getBean("h1");
        h1.show();*/
        /*h2.show();*/
        //System.out.println(h1==h2);
    }
}


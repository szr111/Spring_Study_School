import com.yc.Biz.HelloWorld;
import com.yc.Config.JavaConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JavaConfig.class)
public class MyTest2 {

    @Autowired
    @Qualifier(value = "m1")
    HelloWorld hw;

    @Test
    public void Test1(){
        System.out.println("aaa");
    }

}

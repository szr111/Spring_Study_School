import com.yc.biz.UserMapper;
import com.yc.biz.UserMapperImp;
import com.yc.handle.SelfJDKAspect;
import org.junit.Test;


public class MyTest {

    @Test
    public void Test1(){
        UserMapper u=new UserMapperImp();
        SelfJDKAspect selfJDKAspect=new SelfJDKAspect(u);
        UserMapper proxy = (UserMapper) selfJDKAspect.createProxy();
        proxy.add();
        proxy.fint("xiaol");
    }



}

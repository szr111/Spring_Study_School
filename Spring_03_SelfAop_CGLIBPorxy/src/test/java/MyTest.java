import com.yc.Biz.UserMapperBiz;
import com.yc.handle.SelfCglibAspect;

public class MyTest {
    public static void main(String[] args) {
        UserMapperBiz userMapperBiz=new UserMapperBiz();
        SelfCglibAspect selfCglibAspect=new SelfCglibAspect(userMapperBiz);
        UserMapperBiz proxy = (UserMapperBiz) selfCglibAspect.createProxy();
        proxy.add();

    }
}

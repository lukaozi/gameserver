package lucas.common;

import lucas.common.eventbus.EventBus;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lushengkao vip8
 * 2018/10/31 17:20
 */

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:applicationContext.xml"}) //加载配置文件
public class Test {

    @org.junit.Test
    public void t1() {
        EventBus.getInstance().syncSubmit(new HelloEvent());
    }
}

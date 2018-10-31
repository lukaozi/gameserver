package lucas.common;


import lucas.common.eventbus.Subscribe;
import org.springframework.stereotype.Component;

/**
 * @author lushengkao vip8
 * 2018/10/31 17:18
 */
@Component
public class EventTest {

    @Subscribe
    public void test(HelloEvent event) {
        System.out.println("Hello ------- " + event.getId());
    }
}

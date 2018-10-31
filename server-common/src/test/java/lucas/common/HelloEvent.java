package lucas.common;

import lucas.common.eventbus.IEvent;

/**
 * @author lushengkao vip8
 * 2018/10/31 17:19
 */
public class HelloEvent implements IEvent {


    public long getId() {
        return 1000;
    }
}

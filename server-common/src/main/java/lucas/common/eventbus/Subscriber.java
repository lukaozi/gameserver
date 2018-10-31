package lucas.common.eventbus;

import java.lang.reflect.Method;

/**
 * @author lushengkao vip8
 * 2018/10/31 15:23
 */
public class Subscriber {
    //监听者实例
    private Object bean;
    //监听者方法
    private Method method;

    public Subscriber(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }

    public Object getBean() {
        return bean;
    }

    public Method getMethod() {
        return method;
    }
}

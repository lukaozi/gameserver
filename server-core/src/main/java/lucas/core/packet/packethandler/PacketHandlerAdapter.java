package lucas.core.packet.packethandler;

import java.lang.reflect.Method;

/**
 * @author lushengkao vip8
 * 2018/11/9 15:23
 */
public class PacketHandlerAdapter {

    private Object bean;

    private Method method;

    public PacketHandlerAdapter(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}

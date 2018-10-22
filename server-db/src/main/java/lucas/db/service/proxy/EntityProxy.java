package lucas.db.service.proxy;

import lucas.db.entity.IEntity;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author lushengkao vip8
 * 实体代理对象
 * 2018/10/22 16:40
 */
public class EntityProxy<T extends IEntity> implements MethodInterceptor {


    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }
}

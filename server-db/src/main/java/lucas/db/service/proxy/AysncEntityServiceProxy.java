package lucas.db.service.proxy;

import lucas.db.service.IEntityService;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author lushengkao vip8
 * 实体服务对象 异步 缓存-数据库 模式
 * 2018/10/22 16:43
 */
public class AysncEntityServiceProxy<T extends IEntityService> implements MethodInterceptor {

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }
}

package lucas.db.service.proxy;

import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author lushengkao vip8
 * 实体服务对象 同步更新缓存 异步队列更新数据库
 * 2018/10/22 16:43
 */
public class AsyncEntityServiceProxy extends EntityServiceProxy {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }
}

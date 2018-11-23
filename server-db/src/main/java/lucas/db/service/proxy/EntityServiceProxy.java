package lucas.db.service.proxy;

import lucas.db.redis.service.RedisService;
import lucas.db.service.IEntityService;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author lushengkao vip8
 * 实体服务代理对象 同步模式
 * 存储策略：
 * inster 插入的时候先插入mysql 然后再插入缓存
 * query 查询的时候先查询缓存，缓存没有命中则查询mysql，再插入缓存
 * update 先更新mysql 再淘汰缓存（淘汰缓存增加一次cache miss 和 update缓存的代价比较，这里选前者）
 * delete 先删除mysql 再淘汰缓存 如果先淘汰缓存，会造成cache miss的时候重新更新缓存
 *
 * 2018/10/22 16:42
 */
public class EntityServiceProxy implements MethodInterceptor {

    private RedisService redisService;

    private boolean useRedis;

    public EntityServiceProxy(boolean useRedis, RedisService redisService) {
        this.useRedis = useRedis;
        this.redisService = redisService;
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (!useRedis) {
            return methodProxy.invokeSuper(o,objects);
        }
        return null;
    }
}

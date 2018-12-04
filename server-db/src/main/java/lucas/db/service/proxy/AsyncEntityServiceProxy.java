package lucas.db.service.proxy;

import lucas.common.GlobalContant;
import lucas.common.util.ApplicationContextUtils;
import lucas.db.annnotation.CacheOperation;
import lucas.db.entity.AbstractEntity;
import lucas.db.enums.OperationEnum;
import lucas.db.redis.contant.RedisKey;
import lucas.db.service.EntityCacheUtils;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author lushengkao vip8
 * 实体服务对象 同步更新缓存 异步队列更新数据库
 * 2018/10/22 16:43
 */
public class AsyncEntityServiceProxy extends EntityServiceProxy {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        CacheOperation cacheOperation = method.getAnnotation(CacheOperation.class);
        if (cacheOperation == null) {
            return methodProxy.invokeSuper(o, objects);
        }
        if (!GlobalContant.USE_CACHE) {
            return methodProxy.invokeSuper(o, objects);
        }
        RedisKey redisKey = getRedisKey();
        if (redisKey == null) {
            return methodProxy.invokeSuper(o, objects);
        }
        OperationEnum operation = cacheOperation.value();
        switch (operation) {
            case insert:
                result = methodProxy.invokeSuper(o, objects);
                AbstractEntity entity = (AbstractEntity) objects[0];
                EntityCacheUtils.insertToRedis(entity);
                break;
            case query:
                Serializable id = (Serializable) objects[0];
                String key = redisKey.getKey() + id;
                AbstractEntity query = EntityCacheUtils.queryFromRedis(key, getEntityClass());
                if (query == null) {
                    query = (AbstractEntity) methodProxy.invokeSuper(o, objects);
                    EntityCacheUtils.insertToRedis(query);
                } else {
                    ApplicationContext context = ApplicationContextUtils.getApplicationContext();
                    EntityProxyFactory factory = context.getBean(EntityProxyFactory.class);
                    query = factory.createProxyEntity(query);
                }
                result = query;
                break;
            case update:
                AbstractEntity updateEntity = (AbstractEntity) objects[0];
                result = methodProxy.invokeSuper(o, objects);
                EntityCacheUtils.updateEntity(updateEntity);
                break;
            case delete:
                AbstractEntity delEntity = (AbstractEntity) objects[0];
                result = methodProxy.invokeSuper(o, objects);
                EntityCacheUtils.deleteEntity(delEntity);
                break;
            default:
                break;
        }
        return result;
    }
}

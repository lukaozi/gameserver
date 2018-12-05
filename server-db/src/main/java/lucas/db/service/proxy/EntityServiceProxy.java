package lucas.db.service.proxy;

import lucas.common.GlobalContant;
import lucas.common.log.Loggers;
import lucas.common.util.ApplicationContextUtils;
import lucas.db.annnotation.CacheOperation;
import lucas.db.entity.AbstractEntity;
import lucas.db.enums.OperationEnum;
import lucas.db.redis.contant.RedisKey;
import lucas.db.service.EntityCacheUtils;
import org.slf4j.Logger;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author lushengkao vip8
 * 实体服务代理对象 同步模式
 * 存储策略：
 * inster 插入的时候先插入mysql 然后再插入缓存
 * query 查询的时候先查询缓存，缓存没有命中则查询mysql，再插入缓存
 * update 先更新mysql 再淘汰缓存或者更新缓存
 * delete 先删除mysql 再淘汰缓存 如果先淘汰缓存，会造成cache miss的时候重新更新缓存
 * <p>
 * 2018/10/22 16:42
 */
public class EntityServiceProxy implements MethodInterceptor {

    protected static Logger logger = Loggers.REDIS;

    RedisKey redisKey;

    Class<?> entityClass;

    EntityCacheUtils entityCacheUtils;

    EntityServiceProxy(EntityCacheUtils entityCacheUtils) {
        this.entityCacheUtils = entityCacheUtils;
    }

    void setRedisKey(RedisKey redisKey) {
        this.redisKey = redisKey;
    }

    void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        CacheOperation cacheOperation = method.getAnnotation(CacheOperation.class);
        if (cacheOperation == null) {
            return methodProxy.invokeSuper(o, objects);
        }
        if (!GlobalContant.USE_CACHE) {
            return methodProxy.invokeSuper(o, objects);
        }
        if (redisKey == null) {
            return methodProxy.invokeSuper(o, objects);
        }
        OperationEnum operation = cacheOperation.value();
        return intercept0(o, objects, methodProxy, operation);
    }

    protected Object intercept0(Object o, Object[] objects, MethodProxy methodProxy, OperationEnum operation) throws Throwable {
        Object result = null;
        switch (operation) {
            case insert:
                result = methodProxy.invokeSuper(o, objects);
                AbstractEntity entity = (AbstractEntity) objects[0];
                entityCacheUtils.insertToRedis(entity);
                break;
            case query:
                Serializable id = (Serializable) objects[0];
                String redisKey = this.redisKey.getKey() + id;
                AbstractEntity query = entityCacheUtils.queryFromRedis(redisKey, entityClass);
                if (query == null) {
                    query = (AbstractEntity) methodProxy.invokeSuper(o, objects);
                    entityCacheUtils.insertToRedis(query);
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
                entityCacheUtils.updateEntity(updateEntity);
                break;
            case delete:
                AbstractEntity delEntity = (AbstractEntity) objects[0];
                result = methodProxy.invokeSuper(o, objects);
                entityCacheUtils.deleteEntity(delEntity);
                break;
            default:
                break;
        }
        return result;
    }
}

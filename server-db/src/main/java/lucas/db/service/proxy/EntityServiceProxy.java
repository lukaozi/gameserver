package lucas.db.service.proxy;

import com.alibaba.druid.support.spring.stat.SpringStatUtils;
import lucas.common.GlobalContant;
import lucas.common.util.ApplicationContextUtils;
import lucas.db.annnotation.CacheOperation;
import lucas.db.entity.AbstractEntity;
import lucas.db.enums.OperationEnum;
import lucas.db.redis.contant.RedisKey;
import lucas.db.service.EntityCacheUtils;
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

    private RedisKey redisKey;

    private Class<?> entityClass;

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
        switch (operation) {
            case insert:
                result = methodProxy.invokeSuper(o, objects);
                AbstractEntity entity = (AbstractEntity) objects[0];
                EntityCacheUtils.insertToRedis(entity);
                break;
            case query:
                Serializable id = (Serializable) objects[0];
                String redisKey = this.redisKey.getKey() + id;
                AbstractEntity query = EntityCacheUtils.queryFromRedis(redisKey, entityClass);
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

    public RedisKey getRedisKey() {
        return redisKey;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }
}

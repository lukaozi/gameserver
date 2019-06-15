package lucas.mysql.service.proxy;

import lucas.mysql.entity.AbstractEntity;
import lucas.mysql.enums.OperationEnum;
import lucas.redis.contant.RedisKey;
import lucas.redis.lock.RedisLock;
import lucas.mysql.service.AsyncEntityUtils;
import lucas.mysql.service.EntityCacheUtils;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.Serializable;

/**
 * @author lushengkao vip8
 * 实体服务对象 同步更新缓存 异步队列更新数据库
 * 2018/10/22 16:43
 */
public class AsyncEntityServiceProxy extends EntityServiceProxy {

    private AsyncEntityUtils asyncEntityUtils;

    AsyncEntityServiceProxy(EntityCacheUtils entityCacheUtils, AsyncEntityUtils asyncEntityUtils) {
        super(entityCacheUtils);
        this.asyncEntityUtils = asyncEntityUtils;
    }

    @Override
    protected Object intercept0(Object o, Object[] objects, MethodProxy methodProxy, OperationEnum operation) throws Throwable {
        Object result = null;
        switch (operation) {
            case insert:
                AbstractEntity entity = (AbstractEntity) objects[0];
                if (redisKey != null) {
                    entityCacheUtils.insertToRedis(entity);
                }
                asyncEntityUtils.pushInsertEntity(entity);
                break;
            case query:
                Serializable id = (Serializable) objects[0];
                String key = redisKey.getKey() + id;
                AbstractEntity query = entityCacheUtils.queryFromRedis(key, entityClass);
                if (query == null) {
                    RedisLock redisLock = new RedisLock(RedisKey.PLAYER.getKey() + id);
                    try {
                        redisLock.lock();
                        query = entityCacheUtils.queryFromRedis(key, entityClass);
                        if (query != null) {
                            return query;
                        }
                        query = (AbstractEntity) methodProxy.invokeSuper(o, objects);
                        entityCacheUtils.insertToRedis(query);
                    } finally {
                        redisLock.unlock();
                    }
                } else {
                    query = factory.createProxyEntity(query);
                }
                result = query;
                break;
            case update:
                AbstractEntity updateEntity = (AbstractEntity) objects[0];
                entityCacheUtils.updateEntity(updateEntity);
                asyncEntityUtils.pushUpdateEntity(updateEntity);
                break;
            case delete:
                AbstractEntity delEntity = (AbstractEntity) objects[0];
                asyncEntityUtils.pushDeleteEntity(delEntity);
                break;
            default:
                break;
        }
        return result;
    }
}

package lucas.db.service.proxy;

import lucas.db.entity.AbstractEntity;
import lucas.db.enums.OperationEnum;
import lucas.db.service.AsyncEntityUtils;
import lucas.db.service.EntityCacheUtils;
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
    protected Object intercept0(Object o, Object[] objects, MethodProxy methodProxy, OperationEnum operation) {
        Object result = null;
        switch (operation) {
            case insert:
                AbstractEntity entity = (AbstractEntity) objects[0];
                entityCacheUtils.insertToRedis(entity);
                asyncEntityUtils.pushInsertEntity(entity);
                break;
            case query:
                Serializable id = (Serializable) objects[0];
                String key = redisKey.getKey() + id;
                AbstractEntity query = entityCacheUtils.queryFromRedis(key, entityClass);
                if (query == null) {
                    //TODO
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
                asyncEntityUtils.deleteEntity(delEntity);
                break;
            default:
                break;
        }
        return result;
    }
}

package lucas.db.service.async.pool;

import com.alibaba.druid.support.spring.stat.SpringStatUtils;
import lucas.common.util.ApplicationContextUtils;
import lucas.common.util.BeanUtils;
import lucas.db.entity.AbstractEntity;
import lucas.db.enums.OperationEnum;
import lucas.db.mapper.IDBMapper;
import lucas.db.redis.service.RedisService;
import lucas.db.service.EntityCacheUtils;
import lucas.db.service.EntityService;
import lucas.db.service.proxy.AsyncEntityWrapper;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @author lushengkao vip8
 * 2018/12/11 22:14
 */
public class AsyncSaveTask implements Runnable {

    private RedisService redisService;

    private String redisKey;

    private EntityService entityService;

    AsyncSaveTask(String redisKey, RedisService redisService, EntityService entityService) {
        this.redisKey = redisKey;
        this.redisService = redisService;
        this.entityService = entityService;
    }

    @Override
    public void run() {
        long num = redisService.scard(redisKey);
        for (int i = 0; i < num; i++) {
            String data = redisService.lpop(redisKey);
            if (data == null) {
                continue;
            }
            AsyncEntityWrapper asyncEntityWrapper = new AsyncEntityWrapper();
            asyncEntityWrapper.deserialize(data);
            saveEntity(asyncEntityWrapper);
        }
    }

    @SuppressWarnings("unchecked")
    private void saveEntity(AsyncEntityWrapper asyncEntityWrapper) {
        OperationEnum operationEnum = asyncEntityWrapper.getOperationEnum();
        Map<String, String> params = asyncEntityWrapper.getParams();
        Class entityClass = entityService.getEntityClass();
        switch (operationEnum) {
            case insert:
                AbstractEntity entity = (AbstractEntity)BeanUtils.getObjectFromMap(entityClass, params);
                entityService.insertEntity(entity);
                break;
            case update:
                IDBMapper mapper = entityService.getEntityMapper();
                mapper.updateEntityByMap(params);
                break;
            case delete:
                AbstractEntity delEntity = (AbstractEntity)BeanUtils.getObjectFromMap(entityClass, params);
                entityService.deleteEntity(delEntity);
                ApplicationContext context = ApplicationContextUtils.getApplicationContext();
                EntityCacheUtils entityCacheUtils = context.getBean(EntityCacheUtils.class);
                entityCacheUtils.deleteEntity(delEntity);
                break;
            default:
                break;
        }
    }
}

package lucas.db.service;

import lucas.db.entity.AbstractEntity;
import lucas.db.enums.OperationEnum;
import lucas.db.redis.service.RedisService;
import lucas.db.service.proxy.AsyncEntityWrapper;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lushengkao vip8
 * <p>
 * 2018/12/5 21:27
 */
@Component
public class AsyncEntityUtils {

    private RedisService redisService;

    public AsyncEntityUtils(RedisService redisService) {
        this.redisService = redisService;
    }

    public void pushInsertEntity(AbstractEntity entity) {
        Map<String, String> map = EntityUtils.getCacheValueMap(entity);
        AsyncEntityWrapper wrapper = createAsyncEntityWrapper(OperationEnum.insert, map);
        pushEntity2Redis(entity, wrapper);
    }

    public void pushUpdateEntity(AbstractEntity updateEntity) {
        Map<String, String> changeMap = EntityUtils.getChangeMap(updateEntity);
        AsyncEntityWrapper wrapper = createAsyncEntityWrapper(OperationEnum.update, changeMap);
        pushEntity2Redis(updateEntity, wrapper);
    }

    public void pushDeleteEntity(AbstractEntity delEntity) {
        AsyncEntityWrapper wrapper = createAsyncEntityWrapper(OperationEnum.update, null);
        pushEntity2Redis(delEntity, wrapper);
    }

    private AsyncEntityWrapper createAsyncEntityWrapper(OperationEnum operationEnum, Map<String, String> params) {
        AsyncEntityWrapper wrapper = new AsyncEntityWrapper();
        wrapper.setCreateTime(System.currentTimeMillis());
        wrapper.setOperationEnum(operationEnum);
        wrapper.setParams(params);
        return wrapper;
    }

    private void pushEntity2Redis(AbstractEntity entity, AsyncEntityWrapper wrapper) {
        String className = entity.getClass().getSimpleName();
        String redisUnionKey = EntityUtils.getRedisUnionKey(entity);
        String redisKey = EntityUtils.getRedisKey(entity);
        //先加入玩家个人队列
        redisService.rPushString(redisKey,wrapper.serialize());
        //玩家加入总列表 顺序不能乱
        redisService.sAddString(className,redisUnionKey);
    }
}

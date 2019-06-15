package lucas.mysql.service;

import lucas.common.GlobalConstant;
import lucas.mysql.entity.AbstractEntity;
import lucas.mysql.enums.OperationEnum;
import lucas.redis.service.RedisService;
import lucas.mysql.service.proxy.AsyncEntityWrapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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
        Map<String, String> changeMap = EntityUtils.getChangeMap(delEntity);
        AsyncEntityWrapper wrapper = createAsyncEntityWrapper(OperationEnum.update, changeMap);
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
        //玩家名称
        String redisUnionKey = EntityUtils.getRedisUnionKey(entity);
        //类型名称
        String classRedisKey = className + "#" + GlobalConstant.getServerNo();
        //先加入玩家个人队列
        redisService.rpushString(redisUnionKey, wrapper.serialize());
        //玩家加入总列表 顺序不能乱
        redisService.saddString(classRedisKey, redisUnionKey);
    }
}

package lucas.db.service;

import lucas.common.log.Loggers;
import lucas.common.util.BeanUtils;
import lucas.db.entity.AbstractEntity;
import lucas.db.redis.RedisInterface;
import lucas.db.redis.service.RedisService;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @author lushengkao vip8
 * 2018/11/23 17:23
 */
@Component
public class EntityCacheUtils {

    private static Logger logger = Loggers.REDIS;

    private RedisService redisService;

    public EntityCacheUtils(RedisService service) {
        redisService = service;
    }

    public void insertToRedis(AbstractEntity entity) {
        if (!(entity instanceof RedisInterface)) {
            return;
        }
        String redisKey = EntityUtils.getRedisUnionKey(entity);
        Map<String, String> valueMap = EntityUtils.getCacheValueMap(entity);
        redisService.setMap(redisKey, valueMap);
    }

    public AbstractEntity queryFromRedis(String redisKey, Class<?> clazz) {
        Map<String, String> map = redisService.getMap(redisKey);
        Object result =  BeanUtils.getObjectFromMap(clazz, map);
        if (result instanceof AbstractEntity) {
            return (AbstractEntity) result;
        }
        return null;
    }

    public void deleteEntity(AbstractEntity delEntity) {
        String redisKey = EntityUtils.getRedisUnionKey(delEntity);
        redisService.deleteKey(redisKey);
    }

    public void updateEntity(AbstractEntity updateEntity) {
        Map<String, String> changeMap = EntityUtils.getChangeMap(updateEntity);
        String redisKey = EntityUtils.getRedisUnionKey(updateEntity);
        redisService.setMap(redisKey, changeMap);
    }
}

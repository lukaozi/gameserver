package lucas.db.service;

import lucas.common.log.Loggers;
import lucas.common.util.BeanUtils;
import lucas.common.util.FastJsonUtils;
import lucas.db.annnotation.CacheField;
import lucas.db.entity.AbstractEntity;
import lucas.db.redis.RedisInterface;
import lucas.db.redis.service.RedisService;
import lucas.db.service.proxy.EntityProxy;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author lushengkao vip8
 * 2018/11/23 17:23
 */
@Component
public class EntityCacheUtils {

    private static RedisService redisService;

    private static Logger logger = Loggers.REDIS;

    public EntityCacheUtils(RedisService service) {
        redisService = service;
    }

    public static void insertToRedis(AbstractEntity entity) {
        if (!(entity instanceof RedisInterface)) {
            return;
        }
        String redisKey = getRedisKey(entity);
        Map<String, String> valueMap = getCacheValueMap(entity);
        redisService.setMap(redisKey,valueMap);
    }

    public static AbstractEntity queryFromRedis(String redisKey,Class<?> clazz) {
        Map<String, String> map = redisService.getMap(redisKey);
        Object result = getObjectFromMap(map,clazz);
        if (result instanceof AbstractEntity) {
            return (AbstractEntity) result;
        }
        return null;
    }

    private static Object getObjectFromMap(Map<String, String> map, Class<?> clazz) {
        try {
            Object result = clazz.newInstance();
            return BeanUtils.getObjectFromMap(result,map);
        } catch (Exception e) {
            logger.error("redis 生成临时对象失败");
        }
        return null;
    }

    private static Map<String, String> getCacheValueMap(AbstractEntity entity) {
        Class<? extends AbstractEntity> entityClass = entity.getClass();
        List<Field> fields = BeanUtils.getFieldsWithAnnotation(entityClass, CacheField.class);
        Map<String, String> result = new HashMap<>();
        for (Field field : fields) {
            Object value = null;
            field.setAccessible(true);
            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                logger.error("获取值异常 + class:" + entity.getClass().getSimpleName()
                        + "-id :" + entity.getId() + "-fieldName :" + field.getName());
            }
            result.put(field.getName(), FastJsonUtils.toJson(value));
        }
        return result;
    }

    private static String getRedisKey(AbstractEntity entity) {
        RedisInterface redisEntity = (RedisInterface) entity;
        return redisEntity.getRedisKey().getKey() + entity.getId();
    }

    public static void deleteEntity(AbstractEntity delEntity) {
        String redisKey = getRedisKey(delEntity);
        redisService.deleteKey(redisKey);
    }

    @SuppressWarnings("unchecked")
    public static void updateEntity(AbstractEntity updateEntity) {
        EntityProxy proxy = updateEntity.getProxy();
        Map<String, Object>  change = proxy.getChangeParamMap();
        if (MapUtils.isEmpty(change)) {
            return;
        }
        Map<String,String> valueMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : change.entrySet()) {
            valueMap.put(entry.getKey(),FastJsonUtils.toJson(entry.getValue()));
        }
        String redisKey = getRedisKey(updateEntity);
        redisService.setMap(redisKey,valueMap);
    }
}

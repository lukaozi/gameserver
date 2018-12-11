package lucas.db.service;


import lucas.common.log.Loggers;
import lucas.common.util.BeanUtils;
import lucas.common.util.SQLFileStringUtils;
import lucas.db.annnotation.CacheField;
import lucas.db.entity.AbstractEntity;
import lucas.db.redis.RedisInterface;
import lucas.db.service.proxy.EntityProxy;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lushengkao vip8
 * 2018/12/11 13:42
 */
class EntityUtils {

    private final static Logger LOGGER = Loggers.REDIS;

    static Map<String, String> getCacheValueMap(AbstractEntity entity) {
        Class<? extends AbstractEntity> entityClass = entity.getClass();
        List<Field> fields = BeanUtils.getFieldsWithAnnotation(entityClass, CacheField.class);
        Map<String, String> result = new HashMap<>();
        for (Field field : fields) {
            Object value = null;
            field.setAccessible(true);
            try {
                value = field.get(entity);
            } catch (IllegalAccessException e) {
                LOGGER.error("获取值异常 + class:" + entity.getClass().getSimpleName()
                        + "-id :" + entity.getId() + "-fieldName :" + field.getName());
            }
            result.put(field.getName(), SQLFileStringUtils.toString(value));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    static Map<String, String> getChangeMap(AbstractEntity updateEntity) {
        EntityProxy proxy = updateEntity.getProxy();
        Map<String, Object> change = proxy.getChangeParamMap();
        if (MapUtils.isEmpty(change)) {
            return null;
        }
        Map<String, String> valueMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : change.entrySet()) {
            valueMap.put(entry.getKey(), SQLFileStringUtils.toString(entry.getValue()));
        }
        return valueMap;
    }

    public static String getRedisUnionKey(AbstractEntity entity) {
        RedisInterface redisEntity = (RedisInterface) entity;
        return redisEntity.getRedisKey().getKey() + entity.getId();
    }

    public static String getRedisKey(AbstractEntity entity) {
        RedisInterface redisEntity = (RedisInterface) entity;
        return redisEntity.getRedisKey().getKey();
    }
}

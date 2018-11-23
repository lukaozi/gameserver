package lucas.db.service;

import lucas.db.entity.AbstractEntity;
import lucas.db.redis.service.RedisService;
import org.springframework.stereotype.Component;


/**
 * @author lushengkao vip8
 * 2018/11/23 17:23
 */
@Component
public class EntityCacheUtils {

    private static RedisService redisService;

    public EntityCacheUtils(RedisService service) {
        redisService = service;
    }

    public static void insertToRedis(AbstractEntity entity) {

    }
}

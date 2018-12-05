package lucas.db.service;

import lucas.db.entity.AbstractEntity;
import lucas.db.redis.service.RedisService;
import org.springframework.stereotype.Component;

/**
 * @author lushengkao vip8
 *
 * 2018/12/5 21:27
 */
@Component
public class AsyncEntityUtils {

    private RedisService redisService;

    public AsyncEntityUtils(RedisService redisService) {
        this.redisService = redisService;
    }

    public void pushInsertEntity(AbstractEntity entity) {

    }

    public void pushUpdateEntity(AbstractEntity updateEntity) {

    }

    public void deleteEntity(AbstractEntity delEntity) {

    }
}

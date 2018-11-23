package lucas.db.redis;

import lucas.db.redis.contant.RedisKey;

/**
 * @author lushengkao vip8
 * 2018/11/23 17:39
 */
public interface RedisInterface {

    /**
     * @return 返回redis key的类型
     */
    RedisKey getRedisKey();

}

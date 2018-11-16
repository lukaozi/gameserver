package lucas.db.redis.service;

import lucas.common.log.Loggers;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

/**
 * @author lushengkao vip8
 * 2018/10/26 11:48
 */
@Service
public class RedisService {

    private Logger logger = Loggers.REDIS;

    //redis连接池
    private JedisPool jedisPool;
}

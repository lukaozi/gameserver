package lucas.db.redis.service;

import lucas.common.log.Loggers;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;

/**
 * @author lushengkao vip8
 * 2018/11/23 16:24
 */
@Component
public class JedisPoolHelper {

    private Logger logger = Loggers.REDIS;

    private JedisPool jedisPool;

    public JedisPoolHelper(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @PostConstruct
    private void init() {
        Jedis jedis = getResource();
        jedis.ping();
        logger.info("redis init success");
        jedis.close();
    }

    public Jedis getResource() {
        return jedisPool.getResource();
    }
}

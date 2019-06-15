package lucas.redis.service;

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
public class JedisHelper {

    private Logger logger = Loggers.REDIS;

    private JedisPool jedisPool;

    public JedisHelper(JedisPool jedisPool) {
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

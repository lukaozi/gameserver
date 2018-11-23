package lucas.db.redis.service;

import lucas.common.log.Loggers;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lushengkao vip8
 * 2018/10/26 11:48
 */
@Service
public class RedisService {

    private Logger logger = Loggers.REDIS;

    private JedisPoolHelper helper;

    @Autowired
    public void setJedisPoolHelper(JedisPoolHelper helper) {
        this.helper = helper;
    }
}

package lucas.redis.service;

import lucas.common.log.Loggers;
import lucas.redis.contant.RedisConstant;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * @author lushengkao vip8
 * 2018/10/26 11:48
 */
@SuppressWarnings("ALL")
@Service
public class RedisService {

    private Logger logger = Loggers.REDIS;

    private JedisHelper helper;

    @Autowired
    public void setJedisPoolHelper(JedisHelper helper) {
        this.helper = helper;
    }

    public void setMap(String redisKey, Map<String, String> valueMap) {
        try (Jedis jedis = helper.getResource()) {
            jedis.hmset(redisKey, valueMap);
            //设置缓存时间
            jedis.expire(redisKey, RedisConstant.NORMAL_LIFE);
        } catch (Exception e) {
            logger.error("插入缓存异常，redis key：" + redisKey + ",value : " + valueMap.toString());
        }
    }

    public Map<String, String> getMap(String redisKey) {
        try (Jedis jedis = helper.getResource()) {
            Map<String, String> map = jedis.hgetAll(redisKey);
            jedis.expire(redisKey, RedisConstant.NORMAL_LIFE);
            return map;
            //设置缓存时间
        } catch (Exception e) {
            logger.error("查询缓存异常，redis key：" + redisKey);
        }
        return null;
    }

    public boolean deleteKey(String redisKey) {
        boolean success = false;
        try (Jedis jedis = helper.getResource()) {
            jedis.del(redisKey);
            success = true;
        } catch (Exception e) {
            logger.error("淘汰缓存异常，redis key：" + redisKey);
        }
        return success;
    }

    public void rpushString(String redisKey, String value) {
        try (Jedis jedis = helper.getResource()) {
            jedis.rpush(redisKey,value);
        } catch (Exception e) {
            logger.error("加入队列异常，redis key：" + redisKey);
        }
    }

    public void saddString(String redisKey, String value) {
        try (Jedis jedis = helper.getResource()) {
            jedis.sadd(redisKey,value);
        } catch (Exception e) {
            logger.error("sAdd异常，redis key：" + redisKey);
        }
    }

    public long scard(String redisKey) {
        try (Jedis jedis = helper.getResource()) {
            return jedis.scard(redisKey);
        } catch (Exception e) {
            logger.error("返回集合数量异常，redis key：" + redisKey);
        }
        return 0;
    }

    public String lpop(String redisKey) {
        try (Jedis jedis = helper.getResource()) {
            return jedis.lpop(redisKey);
        } catch (Exception e) {
            logger.error("返回集合数量异常，redis key：" + redisKey);
        }
        return null;
    }
}

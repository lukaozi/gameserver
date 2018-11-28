package lucas.db.redis.service;

import lucas.common.log.Loggers;
import lucas.db.redis.contant.RedisContant;
import lucas.db.redis.lock.RedisLock;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Map;

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

    public void setMap(String redisKey, Map<String, String> valueMap) {
        try (Jedis jedis = helper.getResource()) {
            jedis.hmset(redisKey, valueMap);
            //设置缓存时间
            jedis.expire(redisKey, RedisContant.NORMAL_LIFE);
        } catch (Exception e) {
            logger.error("插入缓存异常，redis key：" + redisKey + ",value : " + valueMap.toString());
        }
    }

    public Map<String, String> getMap(String redisKey) {
        try (Jedis jedis = helper.getResource()) {
            Map<String, String> map = jedis.hgetAll(redisKey);
            jedis.expire(redisKey, RedisContant.NORMAL_LIFE);
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

    public boolean tryLock(String lockKey, String requestId, int expireTime) {
        try (Jedis jedis = helper.getResource()) {
            return RedisLock.tryGetDistributedLock(jedis, lockKey, requestId, expireTime);
        } catch (Exception e) {
            logger.error("获取redis锁失败，redis key：" + lockKey);
        }
        return false;
    }

    public boolean releaseLock(String lockKey, String requestId) {
        try (Jedis jedis = helper.getResource()) {
            return RedisLock.releaseDistributedLock(jedis, lockKey, requestId);
        } catch (Exception e) {
            logger.error("淘汰缓存异常，redis key：" + lockKey);
        }
        return false;
    }
}

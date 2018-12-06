package lucas.db.redis.lock;

import lucas.common.log.Loggers;
import lucas.common.util.ApplicationContextUtils;
import lucas.db.redis.service.JedisHelper;
import lucas.db.utils.idgenerator.IDGenerator;
import lucas.db.utils.idgenerator.IDType;
import org.slf4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * @author lushengkao vip8
 * 2018/11/28 18:32
 */
public class RedisLock {

    private static final Logger logger = Loggers.REDIS;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    //超时时间
    private static final int EXPIRE_TIME = 30000;
    //请求唯一id 确保只有一个客户端能解锁
    private volatile String lockRequestId;

    private static JedisHelper jedisHelper = ApplicationContextUtils.getApplicationContext().getBean(JedisHelper.class);

    private String lockKey;

    public RedisLock(String lockKey) {
        this.lockKey = lockKey;
    }

    /**
     * 获取分布式锁 通过传入 request id 确保其他客户端不能解除本机的锁
     * setnx 原子性加锁
     */
    public void lock() {
        for (;;) {
            if (tryLock()) {
                return;
            }
        }
    }

    /**
     * 尝试获取分布式锁 通过传入 request id 确保其他客户端不能解除本机的锁
     * setnx 原子性加锁
     * @return 是否获取成功
     */
    public boolean tryLock() {
        try (Jedis jedis = jedisHelper.getResource()) {
            String requestId = IDGenerator.createId(IDType.REDIS_LOCK).toString();
            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, EXPIRE_TIME);
            boolean success = LOCK_SUCCESS.equals(result);
            if (success) {
                lockRequestId = requestId;
            }
            return success;
        } catch (Exception e) {
            logger.error("获取redis锁失败，redis key：" + lockKey);
        }
        return false;
    }

    /**
     * 释放分布式锁
     *
     * 利用redis执行lua脚本的时候redis停止运行的特性使原子性解锁
     * @return 是否释放成功
     */
    public boolean unlock() {
        if (lockRequestId == null) {
            return false;
        }
        try (Jedis jedis = jedisHelper.getResource()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(lockRequestId));
            return RELEASE_SUCCESS.equals(result);
        } catch (Exception e) {
            logger.error("释放redis锁失败，redis key：" + lockKey);
        }
        return false;
    }

}

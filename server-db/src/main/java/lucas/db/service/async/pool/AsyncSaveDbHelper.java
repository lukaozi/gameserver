package lucas.db.service.async.pool;

import lucas.common.GlobalConstant;
import lucas.common.service.IService;
import lucas.common.service.ServiceName;
import lucas.db.EntityServiceBeanPostProcessor;
import lucas.db.redis.service.RedisService;
import lucas.db.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author lushengkao vip8
 * 2018/12/11 20:37
 */
@Component
public class AsyncSaveDbHelper implements IService {

    private RedisService redisService;

    private ConcurrentHashMap<String,EntityService> entityServiceMap;

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    private Map<String, ScheduledExecutorService> executors;

    @PostConstruct
    private void init() {
        this.executors = new ConcurrentHashMap<>();
        this.entityServiceMap = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, EntityService> serviceMap = EntityServiceBeanPostProcessor.serviceMap;
        for (Map.Entry<String, EntityService> entry : serviceMap.entrySet()) {
            String classRedisKey = entry.getKey() + "#" + GlobalConstant.SERVER_NO;
            executors.put(classRedisKey, Executors.newSingleThreadScheduledExecutor());
            entityServiceMap.put(classRedisKey,entry.getValue());
        }
    }

    @Override
    public String getId() {
        return ServiceName.ASYNC_DB_SERVICE;
    }

    @Override
    public void startUp() {
        for (Map.Entry<String, ScheduledExecutorService> entry : executors.entrySet()) {
            ScheduledExecutorService executorService = entry.getValue();
            String classRedisKey = entry.getKey();
            EntityService entityService = entityServiceMap.get(classRedisKey);
            AsyncSaveTask command = new AsyncSaveTask(classRedisKey, redisService,entityService);
            executorService.scheduleWithFixedDelay(command, 0L, 3L, TimeUnit.SECONDS);
        }
    }

    @Override
    public void shutDown() {
        for (ScheduledExecutorService executor : executors.values()) {
            executor.shutdown();
            try {
                if (executor.awaitTermination(10L, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

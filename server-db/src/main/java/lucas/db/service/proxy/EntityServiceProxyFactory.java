package lucas.db.service.proxy;

import lucas.db.redis.service.RedisService;
import lucas.db.service.EntityService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Service;


/**
 * @author lushengkao vip8
 * 同步的存储服务生成器
 * 2018/10/26 11:34
 */
@Service
public class EntityServiceProxyFactory {

    private RedisService redisService;

    private boolean uesRedis;

    @Autowired
    public void setRedisService(RedisService redisService) {
        this.redisService = redisService;
    }

    public <T extends EntityService> T creaetEntityServiceProxy(T entityService) throws Exception{
        return createProxy(entityService);
    }

    private <T extends EntityService> T createProxy(T entityService) throws Exception {
        EntityServiceProxy<T> entityServiceProxy = new EntityServiceProxy<>(uesRedis, redisService);
        T proxy = createProxy0(entityService, entityServiceProxy);
        BeanUtils.copyProperties(proxy,entityService);
        return proxy;
    }

    @SuppressWarnings("unchecked")
    private <T extends EntityService> T createProxy0(T entityService, EntityServiceProxy<T> entityServiceProxy) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(entityService.getClass());
        enhancer.setCallback(entityServiceProxy);
        return (T) enhancer.create();
    }
}

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

    public <T extends EntityService> T createEntityServiceProxy(T entityService) throws Exception{
        return createProxy(entityService);
    }

    private <T extends EntityService> T createProxy(T entityService) throws Exception {
        EntityServiceProxy entityServiceProxy = new EntityServiceProxy(uesRedis, redisService);
        T proxy = createProxy0(entityService, entityServiceProxy);
        BeanUtils.copyProperties(proxy,entityService);
        return proxy;
    }

    @SuppressWarnings("unchecked")
    private <T extends EntityService> T createProxy0(T entityService, EntityServiceProxy entityServiceProxy) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(entityService.getClass());
        enhancer.setCallback(entityServiceProxy);
        return (T) enhancer.create();
    }

    public Object createEntityServiceProxy(Object object) throws Exception {
        if (object instanceof EntityService) {
            EntityServiceProxy entityServiceProxy = new EntityServiceProxy(uesRedis, redisService);
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(object.getClass());
            enhancer.setCallback(entityServiceProxy);
            Object proxy = enhancer.create();
            BeanUtils.copyProperties(proxy,object);
            return proxy;
        }
        throw new RuntimeException("无法生成 entity service");
    }
}

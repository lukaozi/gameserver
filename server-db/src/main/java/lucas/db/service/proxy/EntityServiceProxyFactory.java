package lucas.db.service.proxy;

import lucas.common.GlobalContant;
import lucas.db.entity.AbstractEntity;
import lucas.db.redis.RedisInterface;
import lucas.db.service.AsyncEntityUtils;
import lucas.db.service.EntityCacheUtils;
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

    private EntityCacheUtils entityCacheUtils;

    private AsyncEntityUtils asyncEntityUtils;

    @Autowired
    public void setEntityCacheUtils(EntityCacheUtils entityCacheUtils) {
        this.entityCacheUtils = entityCacheUtils;
    }

    @Autowired
    public void setAsyncEntityUtils(AsyncEntityUtils asyncEntityUtils) {
        this.asyncEntityUtils = asyncEntityUtils;
    }

    public Object createEntityServiceProxy(Object object, Class<?> clazz) throws Exception {
        if (object instanceof EntityService) {
            EntityServiceProxy entityServiceProxy = new EntityServiceProxy(entityCacheUtils);
            if (GlobalContant.useAysnc) {
                entityServiceProxy = new AsyncEntityServiceProxy(entityCacheUtils,asyncEntityUtils);
            }
            entityServiceProxy.setEntityClass(clazz);
            Object instance = clazz.newInstance();
            if (!(instance instanceof AbstractEntity)) {
                throw new RuntimeException("无法生成 entity service");
            }
            if (instance instanceof RedisInterface) {
                entityServiceProxy.setRedisKey(((RedisInterface) instance).getRedisKey());
            }
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(object.getClass());
            enhancer.setCallback(entityServiceProxy);
            Object proxy = enhancer.create();
            BeanUtils.copyProperties(proxy, object);
            return proxy;
        }
        throw new RuntimeException("无法生成 entity service");
    }
}

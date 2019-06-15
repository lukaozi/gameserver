package lucas.mysql.service.proxy;

import lucas.common.GlobalConstant;
import lucas.mysql.entity.AbstractEntity;
import lucas.redis.RedisInterface;
import lucas.redis.contant.RedisKey;
import lucas.mysql.service.AsyncEntityUtils;
import lucas.mysql.service.EntityCacheUtils;
import lucas.mysql.service.EntityService;
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
            Object instance = clazz.newInstance();
            if (!(instance instanceof AbstractEntity)) {
                throw new RuntimeException("无法生成 entity service");
            }
            RedisKey redisKey = null;
            if (instance instanceof RedisInterface) {
                redisKey = ((RedisInterface) instance).getRedisKey();
            }
            EntityServiceProxy entityServiceProxy;
            if (GlobalConstant.isUseAsync() && redisKey != null && redisKey.isAsync()) {
                entityServiceProxy = new AsyncEntityServiceProxy(entityCacheUtils, asyncEntityUtils);
            } else {
                entityServiceProxy = new EntityServiceProxy(entityCacheUtils);
            }
            entityServiceProxy.setRedisKey(redisKey);
            entityServiceProxy.setEntityClass(clazz);
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

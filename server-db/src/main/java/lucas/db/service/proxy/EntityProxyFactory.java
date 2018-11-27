package lucas.db.service.proxy;

import lucas.db.entity.AbstractEntity;
import lucas.db.entity.IEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Service;

/**
 * @author lushengkao vip8
 * 实体对象代理生成工厂
 * 2018/10/22 19:32
 */
@Service
public class EntityProxyFactory {


    public <T extends AbstractEntity> T createProxyEntity(T entity) throws Exception {
            EntityProxy entityProxy = createProxy(entity);
            AbstractEntity result = createCGLibProxyEntity(entityProxy);
            result.setProxy(entityProxy);
            BeanUtils.copyProperties(result, entity);
            entityProxy.setCollect(true);
            return (T) result;

    }

    @SuppressWarnings("unchecked")
    private <T extends AbstractEntity> T createCGLibProxyEntity(EntityProxy proxy) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(proxy.getSubject().getClass());
        enhancer.setCallback(proxy);
        return (T) enhancer.create();
    }

    @SuppressWarnings("unchecked")
    private <T extends AbstractEntity> EntityProxy createProxy(T entity) {
        return new EntityProxy(entity);
    }
}

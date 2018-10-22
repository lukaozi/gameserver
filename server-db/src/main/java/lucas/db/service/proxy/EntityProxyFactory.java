package lucas.db.service.proxy;

import lucas.db.entity.AbstractEntity;
import lucas.db.entity.IEntity;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.cglib.proxy.Enhancer;


/**
 * @author lushengkao vip8
 * 2018/10/22 19:32
 */
public class EntityProxyFactory {

    public <T extends IEntity> AbstractEntity createProxyEntity(T entity) {
        try {
            EntityProxy entityProxy = createProxy(entity);
            AbstractEntity result = createCGLibProxyEntity(entityProxy);
            result.setProxy(entityProxy);
            BeanUtils.copyProperties(result, entity);
            entityProxy.setCollect(true);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("创建实例代理对象失败 : " + entity.getClass().getName() + "_id:" + entity.getId());
        }
    }

    private <T extends IEntity> T createCGLibProxyEntity(EntityProxy proxy) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(proxy.getSubject().getClass());
        enhancer.setCallback(proxy);
        return (T) enhancer.create();
    }

    private <T extends IEntity> EntityProxy createProxy(T entity) {
        return new EntityProxy(entity);
    }
}

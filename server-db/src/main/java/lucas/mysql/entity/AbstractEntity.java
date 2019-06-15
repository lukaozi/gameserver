package lucas.mysql.entity;

import lucas.mysql.service.proxy.EntityProxy;

import java.io.Serializable;

/**
 * @author lushengkao vip8
 * 2018/10/22 19:40
 */
public abstract class AbstractEntity<T extends Serializable> implements IEntity<T> {

    //代理对象
    private EntityProxy proxy;

    public AbstractEntity() {
    }

    public EntityProxy getProxy() {
        return proxy;
    }

    public void setProxy(EntityProxy proxy) {
        this.proxy = proxy;
    }

    public abstract void serialize();

    public abstract void deserialize();
}

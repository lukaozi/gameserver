package lucas.db.entity;

import lucas.db.service.proxy.EntityProxy;

import java.beans.Transient;
import java.io.Serializable;

/**
 * @author lushengkao vip8
 * 2018/10/22 19:40
 */
public abstract class AbstractEntity<T extends Serializable> implements IEntity<T> {

    //代理对象
    private EntityProxy proxy;

    @Transient
    public EntityProxy getProxy() {
        return proxy;
    }

    @Transient
    public void setProxy(EntityProxy proxy) {
        this.proxy = proxy;
    }
}

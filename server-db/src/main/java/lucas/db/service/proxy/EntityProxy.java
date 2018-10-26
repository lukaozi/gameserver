package lucas.db.service.proxy;

import lucas.db.entity.IEntity;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lushengkao vip8
 * 实体代理对象
 * 2018/10/22 16:40
 */
public class EntityProxy<T extends IEntity> implements MethodInterceptor {

    private T subject;

    //是否初始化完成
    private boolean collect;

    private Map<String,Object> changeParamMap;

    public EntityProxy(T entity) {
        this.changeParamMap = new ConcurrentHashMap<String, Object>();
        this.subject = entity;
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (!collect) {
            methodProxy.invokeSuper(getSubject(),objects);
        }
        return null;
    }

    public Map<String, Object> getChangeParamMap() {
        return changeParamMap;
    }

    public void setChangeParamMap(Map<String, Object> changeParamMap) {
        this.changeParamMap = changeParamMap;
    }

    public T getSubject() {
        return subject;
    }

    public void setSubject(T subject) {
        this.subject = subject;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }
}


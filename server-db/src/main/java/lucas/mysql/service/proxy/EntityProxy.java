package lucas.mysql.service.proxy;

import lucas.common.util.BeanUtils;
import lucas.mysql.annnotation.CacheMethod;
import lucas.mysql.entity.IEntity;
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

    EntityProxy(T entity) {
        this.changeParamMap = new ConcurrentHashMap<>();
        this.subject = entity;
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = methodProxy.invokeSuper(o, objects);
        if (!collect) {
            return result;
        }
        CacheMethod annotation = method.getAnnotation(CacheMethod.class);
        if (annotation == null) {
            return result;
        }
        Object newValue = objects[0];
        String filedName = annotation.value();
        Object oldValue = BeanUtils.getFieldValue(subject, filedName);
        if (!BeanUtils.equalsValue(oldValue,newValue)) {
            changeParamMap.put(filedName,newValue);
        }
        return result;
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


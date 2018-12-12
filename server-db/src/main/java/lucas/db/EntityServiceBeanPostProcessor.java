package lucas.db;

import lucas.common.GlobalConstant;
import lucas.common.util.BeanUtils;
import lucas.db.annnotation.EntityServiceAnnotation;
import lucas.db.service.EntityService;
import lucas.db.service.proxy.EntityServiceProxyFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

/**
 * entity service 注入
 *
 * @author lushengkao vip8
 * 2018/11/21 17:57
 */
@Component
public class EntityServiceBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    public static ConcurrentHashMap<String, EntityService> serviceMap = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, String beanName) throws BeansException {
        return bean;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            EntityServiceAnnotation annotation = field.getAnnotation(EntityServiceAnnotation.class);
            field.setAccessible(true);
            if (annotation != null) {
                Class<?> fieldType = field.getType();
                Type genericSuperclass = fieldType.getGenericSuperclass();
                ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                Type entityType = parameterizedType.getActualTypeArguments()[0];
                try {
                    Class<?> entityClass = Class.forName(entityType.getTypeName());
                    Object entityService = fieldType.newInstance();
                    SqlSessionTemplate sqlSessionTemplate = applicationContext.getBean(SqlSessionTemplate.class);
                    Field entityFiled = BeanUtils.getFiledByName(entityService, "entityClass");
                    assert entityFiled != null;
                    entityFiled.setAccessible(true);
                    ReflectionUtils.setField(entityFiled, entityService, entityClass);
                    Field sqlTempFile = BeanUtils.getFiledByName(entityService, "sqlSessionTemplate");
                    assert sqlTempFile != null;
                    sqlTempFile.setAccessible(true);
                    ReflectionUtils.setField(sqlTempFile, entityService, sqlSessionTemplate);
                    //保存非代理类
                    EntityService service = (EntityService) fieldType.newInstance();
                    service.setSqlSessionTemplate(sqlSessionTemplate);
                    service.setEntityClass(entityClass);
                    serviceMap.put(entityClass.getSimpleName(),service);
                    if (GlobalConstant.isUseAsync()) {
                        EntityServiceProxyFactory factory = applicationContext.getBean(EntityServiceProxyFactory.class);
                        entityService = factory.createEntityServiceProxy(entityService, entityClass);
                    }
                    ReflectionUtils.setField(field, bean, entityService);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return bean;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

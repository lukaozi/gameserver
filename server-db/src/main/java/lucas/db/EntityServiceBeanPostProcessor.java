package lucas.db;

import lucas.common.GlobalContant;
import lucas.common.util.ApplicationContextUtils;
import lucas.common.util.BeanUtils;
import lucas.db.annnotation.EntityServiceAnnotation;
import lucas.db.service.proxy.EntityServiceProxyFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * entity service 注入
 * @author lushengkao vip8
 * 2018/11/21 17:57
 */
@Component
public class EntityServiceBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            EntityServiceAnnotation annotation = field.getAnnotation(EntityServiceAnnotation.class);
            if (annotation != null) {
                Class<?> fieldType = field.getType();
                Type genericSuperclass = fieldType.getGenericSuperclass();
                ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                Type entityType = parameterizedType.getActualTypeArguments()[0];
                try {
                    Class<?> entityClass = Class.forName(entityType.getTypeName());
                    Object entityService = fieldType.newInstance();
                    ApplicationContext applicationContext = ApplicationContextUtils.getApplicationContext();
                    SqlSessionTemplate sqlSessionTemplate = applicationContext.getBean(SqlSessionTemplate.class);
                    Field entityFiled = BeanUtils.getFiledByName(entityService, "entityClass");
                    ReflectionUtils.setField(entityFiled,entityService,entityClass);
                    Field sqlTempFile = BeanUtils.getFiledByName(entityService, "sqlSessionTemplate");
                    ReflectionUtils.setField(sqlTempFile,entityService,sqlSessionTemplate);
                    if (GlobalContant.USE_CACHE) {
                        EntityServiceProxyFactory factory = applicationContext.getBean(EntityServiceProxyFactory.class);
                        entityService = factory.createEntityServiceProxy(entityService);
                    }
                    ReflectionUtils.setField(field,bean,entityService);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return bean;
    }
}

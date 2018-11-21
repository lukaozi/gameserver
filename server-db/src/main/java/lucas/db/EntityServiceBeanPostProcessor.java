package lucas.db;

import lucas.common.GlobalContant;
import lucas.common.util.ApplicationContextUtils;
import lucas.db.annnotation.EntityServiceAnnotation;
import lucas.db.service.proxy.EntityServiceProxyFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * entity service 注入
 * @author lushengkao vip8
 * 2018/11/21 17:57
 */
@Component
public class EntityServiceBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            EntityServiceAnnotation annotation = field.getAnnotation(EntityServiceAnnotation.class);
            if (annotation != null) {
                Class<?> fieldType = field.getType();
                String typeName = fieldType.getGenericSuperclass().getTypeName();
                try {
                    Class<?> entityClass = Class.forName(typeName);
                    Constructor<?>[] constructors = fieldType.getConstructors();
                    Constructor<?> constructor = constructors[1];
                    ApplicationContext applicationContext = ApplicationContextUtils.getApplicationContext();
                    SqlSessionTemplate sqlSessionTemplate = applicationContext.getBean(SqlSessionTemplate.class);
                    Object entityService = constructor.newInstance(entityClass, sqlSessionTemplate);
                    if (GlobalContant.USE_CACHE) {
                        EntityServiceProxyFactory factory = applicationContext.getBean(EntityServiceProxyFactory.class);
                        factory.createEntityServiceProxy(entityService);
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

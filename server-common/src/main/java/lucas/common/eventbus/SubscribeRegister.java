package lucas.common.eventbus;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 前后置处理器 事件注册器
 * @author lushengkao vip8
 * 2018/10/31 14:26
 */
@Component
public class SubscribeRegister implements BeanPostProcessor, ApplicationContextAware, Ordered {

    private EventBus eventBus;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.eventBus = applicationContext.getBean(EventBus.class);
    }

    //前置注册监听者方法
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return eventBus.registerSubscriber(bean);
    }
    //后置无处理
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    //优先级最高
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

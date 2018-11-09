package lucas.core.packet.packethandler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

/**
 * @author lushengkao vip8
 * 2018/11/9 15:05
 */
@Service
public class PacketHandlerRegister implements BeanPostProcessor, ApplicationContextAware, Ordered {

    private PacketHandlerService packetHandlerService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.packetHandlerService = applicationContext.getBean(PacketHandlerService.class);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return packetHandlerService.register(bean);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

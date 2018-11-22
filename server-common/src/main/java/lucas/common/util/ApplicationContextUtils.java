package lucas.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;


/**
 * @author lushengkao vip8
 * 对外提供spring的applicationContext
 * 2018/10/22 12:53
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware, Ordered {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

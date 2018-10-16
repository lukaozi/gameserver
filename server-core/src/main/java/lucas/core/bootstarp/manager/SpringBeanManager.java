package lucas.core.bootstarp.manager;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * @author lushengkao vip8
 * spring bean实例管理
 * 2018/10/16 20:24
 */
@Repository
public class SpringBeanManager {

    private static SpringBeanManager instance;

    @PostConstruct
    private void init(){
        instance = this;
    }

    public static SpringBeanManager getInstance() {
        return instance;
    }
}

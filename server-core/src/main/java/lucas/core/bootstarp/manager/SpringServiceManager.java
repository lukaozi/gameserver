package lucas.core.bootstarp.manager;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * @author lushengkao vip8
 * spring 逻辑实例实例管理
 * 2018/10/16 19:47
 */
@Service
public class SpringServiceManager {

    public static SpringServiceManager instance;

    @PostConstruct
    private void init() {
        instance = this;
    }

    public static SpringServiceManager getInstance() {
        return instance;
    }

    /**
     * 停止服务时的操作
     */
    public void stop() {

    }
}

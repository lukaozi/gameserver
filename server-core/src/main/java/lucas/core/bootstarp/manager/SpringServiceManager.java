package lucas.core.bootstarp.manager;

import lucas.net.NettySessionManager;
import lucas.net.session.NettySessionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * @author lushengkao vip8
 * spring 逻辑实例实例管理
 * 2018/10/16 19:47
 */
@Service
public class SpringServiceManager {

    private static SpringServiceManager instance;

    private NettySessionManager nettySessionManager;

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

    public static void setInstance(SpringServiceManager instance) {
        SpringServiceManager.instance = instance;
    }

    @Autowired
    public NettySessionManager getNettySessionManager() {
        return nettySessionManager;
    }

    public void setNettySessionManager(NettySessionManager nettySessionManager) {
        this.nettySessionManager = nettySessionManager;
    }
}

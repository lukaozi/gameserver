package lucas.core.bootstarp.manager;

import lucas.net.session.NettySessionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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

    private NettySessionBuilder nettySessionBuilder;

    @PostConstruct
    private void init(){
        instance = this;
    }

    public static SpringBeanManager getInstance() {
        return instance;
    }

    public NettySessionBuilder getNettySessionBuilder() {
        return nettySessionBuilder;
    }

    @Autowired
    public void setNettySessionBuilder(NettySessionBuilder nettySessionBuilder) {
        this.nettySessionBuilder = nettySessionBuilder;
    }
}

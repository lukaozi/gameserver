package lucas.gate.socket.bootstarp.manager;

import lucas.gate.packet.packethandler.PacketHelper;
import lucas.gate.socket.net.session.NettySessionBuilder;
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

    private PacketHelper packetHelper;

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

    public PacketHelper getPacketHelper() {
        return packetHelper;
    }

    @Autowired
    public void setPacketHelper(PacketHelper packetHelper) {
        this.packetHelper = packetHelper;
    }
}

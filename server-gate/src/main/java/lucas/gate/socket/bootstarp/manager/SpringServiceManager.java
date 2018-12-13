package lucas.gate.socket.bootstarp.manager;

import lucas.gate.game.player.service.PlayerManager;
import lucas.gate.packet.packethandler.PacketHandlerService;
import lucas.gate.socket.net.session.NettySessionManager;
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

    private PacketHandlerService packetHandlerService;

    private PlayerManager playerManager;

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

    public NettySessionManager getNettySessionManager() {
        return nettySessionManager;
    }

    @Autowired
    public void setNettySessionManager(NettySessionManager nettySessionManager) {
        this.nettySessionManager = nettySessionManager;
    }

    public PacketHandlerService getPacketHandlerService() {
        return packetHandlerService;
    }

    @Autowired
    public void setPacketHandlerService(PacketHandlerService packetHandlerService) {
        this.packetHandlerService = packetHandlerService;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Autowired
    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }
}

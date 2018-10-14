package lucas.net;


import lucas.common.log.Loggers;
import lucas.core.service.IService;
import lucas.net.server.NettyTcpSocketServerService;
import org.slf4j.Logger;

/**
 * create by lushengkao 2018/10/14 23:42
 * 本地所有service管理
 */
public class LocalNetService implements IService {

    Logger logger = Loggers.SERVER_LOGGER;

    //tcp服务
    private NettyTcpSocketServerService nettyTcpSocketServerService;

    @Override
    public String getId() {
        return ServiceName.LOCAL_SERVICE;
    }

    @Override
    public void startUp() {
        initNetServices();
    }

    @Override
    public void shutDown() {

    }

    private void initNetServices() {
        //这里应该根据配置来决定
        nettyTcpSocketServerService = new NettyTcpSocketServerService();
    }
}

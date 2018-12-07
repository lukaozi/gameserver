package lucas.core.socket.net;


import lucas.core.service.IService;
import lucas.core.service.ServiceName;
import lucas.core.socket.net.server.NettyTcpSocketServerService;

/**
 * create by lushengkao 2018/10/14 23:42
 * 本地所有service管理
 */
public class LocalNetService implements IService {

    //tcp服务
    private NettyTcpSocketServerService nettyTcpSocketServerService;

    public String getId() {
        return ServiceName.LOCAL_SERVICE;
    }

    public void startUp() {
        initNetServices();
    }

    public void shutDown() {

    }

    private void initNetServices() {
        //这里应该根据配置来决定
        nettyTcpSocketServerService = new NettyTcpSocketServerService();
        nettyTcpSocketServerService.startServer();
    }
}

package lucas.gate.socket.net;


import lucas.common.service.IService;
import lucas.common.service.ServiceName;
import lucas.gate.socket.net.server.NettyTcpSocketServerService;
import org.springframework.stereotype.Service;

/**
 * create by lushengkao 2018/10/14 23:42
 * 本地所有service管理
 */
@Service
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

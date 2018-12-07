package lucas.teamserver.server;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import lucas.teamserver.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lushengkao vip8
 * 2018/12/7 18:05
 */
@Component
public class ServerRegistry {

//    @Autowired
//    private TeamService teamService;
//
//    public void connect() {
//        // 当前应用配置
//        ApplicationConfig application = new ApplicationConfig();
//        application.setName("team-server");
//
//        // 连接注册中心配置
//        RegistryConfig registry = new RegistryConfig();
//        registry.setAddress("127.0.0.1");
//        registry.setPort(2181);
////        registry.setUsername("aaa");
////        registry.setPassword("bbb");
//
//        // 服务提供者协议配置
//        ProtocolConfig protocol = new ProtocolConfig();
//        protocol.setName("team-server");
//        protocol.setPort(20880);
//        protocol.setThreads(200);
//
//        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
//        // 服务提供者暴露服务配置
//        // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
//        ServiceConfig<TeamService> service = new ServiceConfig<>();
//        service.setApplication(application);
//        // 多个注册中心可以用setRegistries()
//        service.setRegistry(registry);
//        // 多个协议可以用setProtocols()
//        service.setProtocol(protocol);
//        service.setInterface(TeamService.class);
//        // 服务实现
//        service.setRef(teamService);
//        service.setVersion("1.0.0");
//        // 暴露及注册服务
//        service.export();
//    }
}

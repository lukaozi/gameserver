package lucas.rpcserver.teamserver.provider;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import lucas.common.log.Loggers;
import lucas.common.util.ApplicationContextUtils;
import lucas.rpcserver.teamserver.RpcTeamService;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author lushengkao vip8
 * 2018/12/7 18:05
 */
@Component
public class ServerRegistry {

    private final static int THREAD_COUNT = Runtime.getRuntime().availableProcessors() * 2;

    private static final Logger LOGGER = Loggers.RPC;

    public void connect() throws IOException {
        ServiceConfig<RpcTeamService> service = new ServiceConfig<>();
        ApplicationConfig application = new ApplicationConfig("team-server-provider");
        service.setApplication(application);
        RegistryConfig registry = new RegistryConfig("127.0.0.1:2181");
        registry.setProtocol("zookeeper");
        service.setRegistry(registry);
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(12332);
        protocolConfig.setThreads(THREAD_COUNT);
        service.setProtocol(protocolConfig);
        service.setInterface(RpcTeamService.class);
        ApplicationContext applicationContext = ApplicationContextUtils.getApplicationContext();
        TeamServiceImpl teamService = applicationContext.getBean(TeamServiceImpl.class);
        service.setRef(teamService);
        service.export();
        LOGGER.debug("组队服务器启动完成");
        System.in.read();
//
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
//        ApplicationContext context = ApplicationContextUtils.getApplicationContext();
//        TeamServiceImpl teamService = context.getBean(TeamServiceImpl.class);
//        service.setRef(teamService);
//        service.setVersion("1.0.0");
//        // 暴露及注册服务
//        service.export();
    }
}

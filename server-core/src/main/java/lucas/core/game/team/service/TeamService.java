package lucas.core.game.team.service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import lucas.teamserver.RpcTeamService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author lushengkao vip8
 * 2018/12/8 16:28
 */
@Component
public class TeamService {

    private RpcTeamService rpcService;

    private ReferenceConfig<RpcTeamService> referenceConfig;

    @PostConstruct
    public void connectRpc() {
        referenceConfig = new ReferenceConfig<>();
        ApplicationConfig application = new ApplicationConfig();
        application.setName("team-server-consumer");
        referenceConfig.setApplication(application);
        RegistryConfig registry = new RegistryConfig("127.0.0.1");
        registry.setPort(2181);
        registry.setProtocol("zookeeper");
        referenceConfig.setRegistry(registry);
        referenceConfig.setInterface(RpcTeamService.class);
        this.rpcService = referenceConfig.get();
    }
}

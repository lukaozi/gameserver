//package lucas.teamserver.consumer;
//
//import com.alibaba.dubbo.config.ApplicationConfig;
//import com.alibaba.dubbo.config.ReferenceConfig;
//import com.alibaba.dubbo.config.RegistryConfig;
//import lucas.teamserver.RpcTeamService;
//
///**
// * @author lushengkao vip8
// * 2018/12/8 16:02
// */
//public class TeamConsumer {
//
//    public static void main(String[] args) {
//        ReferenceConfig<RpcTeamService> reference = new ReferenceConfig<>();
//        ApplicationConfig application = new ApplicationConfig();
//        application.setName("team-server-consumer");
//        reference.setApplication(application);
//        RegistryConfig registry = new RegistryConfig("127.0.0.1");
//        registry.setPort(2181);
//        registry.setProtocol("zookeeper");
//        reference.setRegistry(registry);
//        reference.setInterface(RpcTeamService.class);
//        RpcTeamService teamService = reference.get();
//        teamService.hello();
//    }
//
//}

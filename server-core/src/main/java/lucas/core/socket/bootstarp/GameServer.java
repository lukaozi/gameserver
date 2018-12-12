package lucas.core.socket.bootstarp;

import lucas.common.log.Loggers;
import lucas.common.service.IService;
import lucas.core.socket.bootstarp.manager.GlobalManager;
import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @author lushengkao vip8
 * 服务初始化入口
 *
 */
public class GameServer {

    private static ClassPathXmlApplicationContext applicationContext;

    private final static Logger logger = Loggers.SERVER_LOGGER;

    public static void main(String[] args) {
        intiSpring();
        GlobalManager.init();
        initService();
        addShutdownHook();
        GameServerRunTime.setOpen(true);
        logger.info("启动完成");
    }

    private static void intiSpring() {
        applicationContext = new ClassPathXmlApplicationContext("server/applicationContext.xml");
        applicationContext.start();
    }

    private static void initService() {
        Map<String, IService> serviceMap = applicationContext.getBeansOfType(IService.class);
        for (IService service : serviceMap.values()) {
            service.startUp();
        }
    }

    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Map<String, IService> serviceMap = applicationContext.getBeansOfType(IService.class);
            for (IService service : serviceMap.values()) {
                service.shutDown();
            }
            //资源回收
            GlobalManager.stop();
            applicationContext.close();
        }));
    }

}

package lucas.core.bootstarp;

import lucas.common.log.Loggers;
import lucas.core.bootstarp.manager.GlobalManager;
import lucas.core.bootstarp.manager.LocalMagager;
import lucas.net.LocalNetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lushengkao vip8
 * 服务初始化入口
 *
 */
public class GameServer {

    private static ClassPathXmlApplicationContext applicationContext;

    final static Logger logger = Loggers.SERVER_LOGGER;

    public static void main(String[] args) {
        intiSpring();
        GlobalManager.init();
        initNetService();
        addShutdownHook();
        GameServerRunTime.setOpen(true);
        logger.info("启动完成");
    }

    private static void intiSpring() {
        applicationContext = new ClassPathXmlApplicationContext("server/*.xml");
        applicationContext.start();
    }

    private static void initNetService() {
        LocalNetService localNetService = new LocalNetService();
        localNetService.startUp();
        LocalMagager.INSTANCE.setLocalNetService(localNetService);
    }

    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                //断开服务
                LocalMagager.INSTANCE.getLocalNetService().shutDown();
                //资源回收
                GlobalManager.stop();
                applicationContext.close();
            }
        }));
    }
}

package lucas.core.socket.bootstarp;

import lucas.common.log.Loggers;
import lucas.common.util.ApplicationContextUtils;
import lucas.core.game.team.service.TeamService;
import lucas.core.socket.bootstarp.manager.GlobalManager;
import lucas.core.socket.bootstarp.manager.LocalMagager;
import lucas.core.socket.bootstarp.manager.SpringServiceManager;
import lucas.core.socket.net.LocalNetService;
import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        initNetService();
        addShutdownHook();
        GameServerRunTime.setOpen(true);
        logger.info("启动完成");
        ApplicationContextUtils.getApplicationContext().getBean(TeamService.class).sayHello();
    }

    private static void intiSpring() {
        applicationContext = new ClassPathXmlApplicationContext("server/applicationContext.xml");
        applicationContext.start();
    }

    private static void initNetService() {
        LocalNetService localNetService = new LocalNetService();
        localNetService.startUp();
        LocalMagager.INSTANCE.setLocalNetService(localNetService);
    }

    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //断开服务
            LocalMagager.INSTANCE.getLocalNetService().shutDown();
            //资源回收
            GlobalManager.stop();
            applicationContext.close();
        }));
    }

}

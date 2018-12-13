package lucas.serverteam;

import lucas.common.log.Loggers;
import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author lushengkao vip8
 * 2018/12/7 17:33
 */
public class ServerStarter {

    private static ClassPathXmlApplicationContext applicationContext;

    private final static Logger logger = Loggers.SERVER_LOGGER;

    public static void main(String[] args) {
        intiSpring();
        addShutdownHook();
        logger.info("启动完成");
    }

    private static void intiSpring() {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        applicationContext.start();
    }

    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> applicationContext.close()));
    }
}

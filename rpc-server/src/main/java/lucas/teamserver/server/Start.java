package lucas.teamserver.server;

import lucas.common.log.Loggers;
import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author lushengkao vip8
 * 2018/12/7 17:33
 */
public class Start {

    private static ClassPathXmlApplicationContext applicationContext;

    private final static Logger logger = Loggers.SERVER_LOGGER;

    public static void main(String[] args) throws IOException {
        intiSpring();
        addShutdownHook();
        logger.info("启动完成");
        ServerRegistry registry = applicationContext.getBean(ServerRegistry.class);
        registry.connect();
        System.in.read();
    }

    private static void intiSpring() {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        applicationContext.start();
    }

    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> applicationContext.close()));
    }
}

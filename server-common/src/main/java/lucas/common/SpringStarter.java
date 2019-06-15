package lucas.common;

import lucas.common.log.Loggers;
import org.slf4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;


/**
 * @author lushengkao vip8
 * 2018/12/7 17:33
 */
public class SpringStarter {

    private final static Logger logger = Loggers.SERVER_LOGGER;

    private String applicationContextPath;

    private static ClassPathXmlApplicationContext applicationContext;

    private CountDownLatch stopHook = new CountDownLatch(1);

    public SpringStarter(String applicationContextPath) {
        this.applicationContextPath = applicationContextPath;
    }

    public void start() throws InterruptedException {
        intiSpring();
        addShutdownHook();
        logger.info("启动完成");
        stopHook.await();
    }

    private void intiSpring() {
        applicationContext = new ClassPathXmlApplicationContext(applicationContextPath);
        applicationContext.start();
    }

    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> applicationContext.close()));
    }

    public void stop() {
        stopHook.countDown();
    }
}

package lucas.teamserver.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author lushengkao vip8
 * 2018/12/7 17:33
 */
public class Start {

    private static ClassPathXmlApplicationContext applicationContext;


    public static void main(String[] args) throws IOException {
        intiSpring();
        addShutdownHook();
        ServerRegistry registry = applicationContext.getBean(ServerRegistry.class);
//        registry.connect();
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

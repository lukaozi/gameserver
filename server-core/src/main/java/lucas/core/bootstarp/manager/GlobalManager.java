package lucas.core.bootstarp.manager;

/**
 * @author lushengkao vip8
 * 2018/10/16 19:53
 */
public class GlobalManager {

    public static void init() {
    }

    public static void stop() {
        SpringServiceManager.getInstance().stop();
    }
}

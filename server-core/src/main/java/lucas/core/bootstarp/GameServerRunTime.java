package lucas.core.bootstarp;

/**
 * @author lushengkao vip8
 * 运行时全局变量
 * 2018/10/16 21:08
 */
public class GameServerRunTime {

    //服务是否开启
    private static volatile boolean open = false;

    public static boolean isOpen() {
        return open;
    }

    public static void setOpen(boolean open) {
        GameServerRunTime.open = open;
    }
}

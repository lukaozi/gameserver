package lucas.common;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author lushengkao vip8
 * 应该移到配置表
 * 2018/11/8 12:02
 */
@Component
@PropertySource("classpath:server.properties")
public class GlobalConstant {

    //魔法码
    @Value("${server.magic_code}")
    private static short MAGIC_CODE = 14;

    //协议版本号
    @Value("${server.version}")
    private static byte VERSION = 1;

    //是否启动redis缓存 同步缓存机制和异步缓存机制
    @Value("${server.use_cache}")
    private static boolean USE_CACHE = false;

    //机器编码 1 ~ 1023 10bit作为机器编码
    @Value("${server.no}")
    private static int SERVER_NO = 1;

    //是否启动异步缓存机制
    @Value("${server.use_async}")
    private static boolean USE_ASYNC = false;

    public static short getMagicCode() {
        return MAGIC_CODE;
    }

    public static byte getVERSION() {
        return VERSION;
    }

    public static boolean isUseCache() {
        return USE_CACHE;
    }

    public static int getServerNo() {
        return SERVER_NO;
    }

    public static boolean isUseAsync() {
        return USE_ASYNC;
    }
}

package lucas.common;


/**
 * @author lushengkao vip8
 * 应该移到配置表
 * 2018/11/8 12:02
 */
public interface GlobalConstant {
    //机器码
    int MAGIC_CODE = 0x2522;
    //协议版本号
    byte VERSION = 1;
    //是否启动redis缓存 同步缓存机制和异步缓存机制
    boolean USE_CACHE = true;
    //机器编码 1 ~ 1023 10bit作为机器编码
    int SERVER_NO = 1;
    //是否启动异步缓存机制
    boolean USE_ASYNC = false;
    //异步redis存贮标志
    String ASYNC_REDIS = "async#";
}

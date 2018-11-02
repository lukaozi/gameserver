package lucas.common.util;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author lushengkao vip8
 * 2018/10/31 16:32
 */
public class ThreadPoolUtil {

    public static ScheduledThreadPoolExecutor[] createSingleExecutorServiceArray(String name,int size) {
        if (size <= 0) {
            throw new RuntimeException("线程数量不能少于0,名称：" + name);
        }
        ScheduledThreadPoolExecutor[] result = new ScheduledThreadPoolExecutor[size];
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory(name);
        for (int i = 0; i < result.length; i++) {
            ScheduledThreadPoolExecutor service = new ScheduledThreadPoolExecutor(1, namedThreadFactory);
            result[i] = service;
        }
        return result;
    }
}

package lucas.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author lushengkao vip8
 * 2018/10/31 16:32
 */
public class ThreadPoolUtil {

    public static ExecutorService[] createSingleExecutorServiceArray(String name,int size) {
        if (size <= 0) {
            throw new RuntimeException("线程数量不能少于0,名称：" + name);
        }
        ExecutorService[] result = new ExecutorService[size];
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory(name);
        for (ExecutorService service : result) {
            service = new ScheduledThreadPoolExecutor(1,namedThreadFactory);
        }
        return result;
    }
}

package lucas.executors;


import lucas.common.util.ThreadPoolUtil;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author lushengkao vip8
 * 线程池组
 * 2018/11/2 15:56
 */
public class ArrayExecutor {

    private int threadpoolSize;

    private ScheduledThreadPoolExecutor[] executorServices;

    private ArrayExecutor(String name, int threadpoolSize) {
        this.threadpoolSize = threadpoolSize;
        executorServices = ThreadPoolUtil.createSingleExecutorServiceArray(name,threadpoolSize);
    }

    public void submit(Runnable runnable,long key) {
        executorServices[(int) (threadpoolSize % key)].submit(runnable);
    }

    public void submit(Runnable runnable,int key) {
        executorServices[threadpoolSize % key].submit(runnable);
    }

    public static ArrayExecutor build(String name,int size) {
        return new ArrayExecutor(name,size);
    }
}

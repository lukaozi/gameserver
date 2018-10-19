package lucas.common.promise;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface ITaskFuture<V> extends Future<V> {
    /**
     * 是否成功
     */
    boolean isSuccess();

    /**
     * 获取执行结果
     */
    V getNow();

    /**
     * 是否可以取消
     */
    boolean isCancellable();

    /**
     * 等待future完成
     */
    ITaskFuture<V> await() throws InterruptedException, ExecutionException;

    /**
     * 等待future超时
     */
    boolean await(long timeOutMills) throws InterruptedException;

    /**
     * 等待future超时完成
     */
    boolean await(long timeOut, TimeUnit timeUnit) throws InterruptedException;

    /**
     * 等待future完成，不响应中断
     */
    ITaskFuture<V> awaitUninterruptibly() throws Exception;

    boolean awaitUninterruptibly(long timeOutMills);

    boolean awaitUninterruptibly(long timeOut, TimeUnit timeUnit);

    ITaskFuture<V> addListener(ITaskFutureListener listener);

    ITaskFuture<V> removeListener(ITaskFutureListener listener);
}

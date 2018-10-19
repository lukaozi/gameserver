package lucas.common.promise;

public interface ITaskFutureListener <V extends ITaskFuture<?>> extends EventListener {

    /**
     *  完成
     * @param future
     */
    void operationComplete(ITaskFuture<V> future) throws Exception;
}

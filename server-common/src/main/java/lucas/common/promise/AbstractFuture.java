package lucas.common.promise;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author lushengkao vip8
 */
public class AbstractFuture<V> implements ITaskFuture<V>  {
    private volatile Object result;
    private Collection<ITaskFutureListener> listeners = new CopyOnWriteArrayList<ITaskFutureListener>();

    /**
     * 当任务正常执行结果为null时, 即客户端调用{@link AbstractFuture#setSuccess}时,
     * result引用该对象
     */
    private static final SuccessSignal SUCCESS_SIGNAL = new SuccessSignal();

    public boolean isSuccess() {
        return result != null && !(result instanceof CauseHolder);
    }

    public V getNow() {
        if (result == SUCCESS_SIGNAL) {
            return  null;
        }
        return  (V)result;
    }

    public boolean isCancellable() {
        return result == null;
    }

    public ITaskFuture<V> await() throws InterruptedException {
        return realAwait(true);
    }

    private ITaskFuture<V> realAwait(boolean interruptable) throws InterruptedException {
        if (!isDone()) { // 若已完成就直接返回了
            // 若允许终端且被中断了则抛出中断异常
            if (interruptable && Thread.interrupted()) {
                throw new InterruptedException("thread " + Thread.currentThread().getName() + " has been interrupted.");
            }

            boolean interrupted = false;
            synchronized (this) {
                while (!isDone()) {
                    try {
                        wait(); // 释放锁进入waiting状态，等待其它线程调用本对象的notify()/notifyAll()方法
                    } catch (InterruptedException e) {
                        if (interruptable) {
                            throw e;
                        } else {
                            interrupted = true;
                        }
                    }
                }
            }

            if (interrupted) {
                // 为什么这里要设中断标志位？因为从wait方法返回后, 中断标志是被clear了的,
                // 这里重新设置以便让其它代码知道这里被中断了。
                Thread.currentThread().interrupt();
            }
        }
        return this;
    }

    private boolean realAwait(long timeoutNanos, boolean interruptable) throws InterruptedException {
        if (isDone()) {
            return true;
        }

        if (timeoutNanos <= 0) {
            return isDone();
        }

        if (interruptable && Thread.interrupted()) {
            throw new InterruptedException(toString());
        }

        long startTime = System.nanoTime();
        long waitTime = timeoutNanos;
        boolean interrupted = false;

        try {
            synchronized (this) {
                if (isDone()) {
                    return true;
                }

                for (;;) {
                    try {
                        wait(waitTime / 1000000, (int) (waitTime % 1000000));
                    } catch (InterruptedException e) {
                        if (interruptable) {
                            throw e;
                        } else {
                            interrupted = true;
                        }
                    }

                    if (isDone()) {
                        return true;
                    } else {
                        waitTime = timeoutNanos - (System.nanoTime() - startTime);
                        if (waitTime <= 0) {
                            return isDone();
                        }
                    }
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean await(long timeOutMills) throws InterruptedException {
        return realAwait(TimeUnit.MILLISECONDS.toNanos(timeOutMills), true);
    }

    public boolean await(long timeOut, TimeUnit timeUnit) throws InterruptedException {
        return realAwait(timeUnit.toNanos(timeOut), true);
    }

    public ITaskFuture<V> awaitUninterruptibly() {
        try {
            return realAwait(false);
        } catch (InterruptedException e) { // 这里若抛异常了就无法处理了
            throw new InternalError();
        }
    }

    public boolean awaitUninterruptibly(long timeOutMills) {
        try {
            return realAwait(TimeUnit.MILLISECONDS.toNanos(timeOutMills),false);
        } catch (InterruptedException e) { // 这里若抛异常了就无法处理了
            throw new InternalError();
        }
    }

    public boolean awaitUninterruptibly(long timeOut, TimeUnit timeUnit) {
        try {
            return realAwait(timeUnit.toNanos(timeOut), false);
        } catch (InterruptedException e) {
            throw new InternalError();
        }
    }


    public ITaskFuture<V> addListener(ITaskFutureListener listener) {
        if (listener == null) {
            throw new NullPointerException("listener");
        }
        if (isDone()) { // 若已完成直接通知该监听器
            notifyListener(listener);
            return this;
        }
        synchronized (this) {
            if (!isDone()) {
                listeners.add(listener);
                return this;
            }
        }
        notifyListener(listener);
        return this;
    }

    public ITaskFuture<V> removeListener(ITaskFutureListener listener) {
        if (listener == null) {
            throw new NullPointerException("listener");
        }

        if (!isDone()) {
            listeners.remove(listener);
        }
        return this;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        if(isDone()){
            return false;
        }

        synchronized (this){
            if(isDone()){
                return false;
            }

            result = new CauseHolder(new CancelException());
            notifyAll();
            notifyListeners();
        }

        return false;
    }

    public boolean isCancelled() {
        return result instanceof CauseHolder && ((CauseHolder) result).cause instanceof CancelException;
    }

    public boolean isDone() {
        return result != null;
    }

    public V get() throws InterruptedException, ExecutionException {
        await();

        Throwable cause = cause();
        if(cause == null){
            return getNow();
        }

        if(cause instanceof CancelException){
            throw new ExecutionException(cause);
        }else{
            throw new ExecutionException(cause);
        }
    }

    public Throwable cause() {
        if (result instanceof CauseHolder) {
            return ((CauseHolder) result).cause;
        }
        return null;
    }

    private void notifyListeners() {
        for (ITaskFutureListener l : listeners) {
            notifyListener(l);
        }
    }

    private void notifyListener(ITaskFutureListener l) {
        try {
            l.operationComplete(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (await(timeout, unit)) {// 超时等待执行结果
            Throwable cause = cause();
            if (cause == null) {// 没有发生异常，异步操作正常结束
                return getNow();
            }
            if (cause instanceof CancelException) {// 异步操作被取消了
                throw new ExecutionException(cause);
            }else {
                throw new ExecutionException(cause);// 其他异常
            }
        }
        // 时间到了异步操作还没有结束, 抛出超时异常
        throw new TimeoutException();
    }

    public ITaskFuture<V> setFailure(Throwable cause) {
        if (setFailure0(cause)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this);
    }

    private boolean setFailure0(Throwable cause) {
        if (isDone()) {
            return false;
        }

        synchronized (this) {
            if (isDone()) {
                return false;
            }
            result = new CauseHolder(cause);
            notifyAll();
        }

        return true;
    }

    public ITaskFuture<V> setSuccess(Object result) {
        // 设置成功后通知监听器
        if (setSuccess0(result)) {
            notifyListeners();
            return this;
        }
        throw new IllegalStateException("complete already: " + this);
    }

    private boolean setSuccess0(Object result) {
        if (isDone()) {
            return false;
        }

        synchronized (this) {
            if (isDone()) {
                return false;
            }
            // 异步操作正常执行完毕的结果是null
            if (result == null) {
                this.result = SUCCESS_SIGNAL;
            } else {
                this.result = result;
            }
            notifyAll();
        }
        return true;
    }

    private static class SuccessSignal {
    }
}

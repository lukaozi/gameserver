package lucas.common.eventbus;

/**
 * 被观察者需要实现的接口
 */
public interface IEvent {

    /**
     * 事件執行
     */
    void run();
}

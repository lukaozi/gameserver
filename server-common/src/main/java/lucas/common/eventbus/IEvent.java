package lucas.common.eventbus;

/**
 * 被观察者需要实现的接口
 */
public interface IEvent {

    /**根据id取模分配异步执行的线程*/
    long getId();

}

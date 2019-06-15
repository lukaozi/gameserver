package lucas.mysql.transaction;

/**
 * @author lushengkao vip8
 * 事务锁
 * 2018/11/28 17:04
 */
public interface TransactionLockInterface {

    /**
     * 销毁事务锁
     */
    void destroy();

    /**
     * 创建事务锁
     * @param waitTime 等待时间
     * @return 是否成功
     */
    boolean create(long waitTime);

    /**
     * @return 锁信息
     */
    String getInfo();

}

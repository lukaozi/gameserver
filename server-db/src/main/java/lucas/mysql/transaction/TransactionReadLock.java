package lucas.mysql.transaction;

/**
 * @author lushengkao vip8
 * 事务读锁
 * 2018/11/28 17:05
 */
public class TransactionReadLock implements TransactionLockInterface{
    @Override
    public void destroy() {

    }

    @Override
    public boolean create(long waitTime) {
        return false;
    }

    @Override
    public String getInfo() {
        return null;
    }
}

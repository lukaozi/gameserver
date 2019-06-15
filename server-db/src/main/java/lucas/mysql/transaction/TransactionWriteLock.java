package lucas.mysql.transaction;

/**
 * @author lushengkao vip8
 * 事务写锁
 * 2018/11/28 17:03
 */
public class TransactionWriteLock implements TransactionLockInterface{
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

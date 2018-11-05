package lucas.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lushengkao vip8
 * 多对象锁
 * 2018/11/5 16:50
 */
public class LinkedLock {

    private static final ConcurrentHashMap<Object,ObjectLock> OBJECT_LOCK_MAP = new ConcurrentHashMap<>();

    private ArrayList<ObjectLock> locks;

    public void lock() {
        for (ReentrantLock lock : locks) {
            lock.lock();
        }
    }

    public void unLock() {
        for (int i = locks.size(); i > 0; i--) {
            locks.get(i - 1).unlock();
        }
    }

    public static LinkedLock buildLock(Object... objects) {
        if (objects == null) {
            throw new RuntimeException("锁对象为空");
        }
        LinkedLock lock = new LinkedLock();
        lock.locks = new ArrayList<>(objects.length);
        for (Object object : objects) {
            lock.locks.add(createLock(object));
        }
        Collections.sort(lock.locks);
        return lock;
    }

    private static ObjectLock createLock(Object object) {
        if (OBJECT_LOCK_MAP.containsKey(object)) {
            return OBJECT_LOCK_MAP.get(object);
        }else {
            ObjectLock lock = new ObjectLock(object,false);
            ObjectLock oldLock = OBJECT_LOCK_MAP.putIfAbsent(object, lock);
            return oldLock == null ? lock : oldLock;
        }
    }
}

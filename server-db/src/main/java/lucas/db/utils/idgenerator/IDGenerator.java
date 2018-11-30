package lucas.db.utils.idgenerator;


import lucas.common.GlobalContant;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * id生成器
 * @author lushengkao vip8
 * 2018/11/27 12:00
 */
public class IDGenerator {

    private static final ConcurrentHashMap<IDType,SnowFlakeIdWorker> SNOW_FLAKE_ID_WORKER_MAP= new ConcurrentHashMap<>();

    private final static ConcurrentHashMap<IDType, AtomicLong> LOCAL_ID_MAP = new ConcurrentHashMap<>();

    public static Long createId(IDType type) {
        if (type.isSave()) {
           return createIdBySnowFlake(type);
        }else {
            return createIdLocal(type);
        }
    }

    private static Long createIdLocal(IDType type) {
        AtomicLong atomicLong = LOCAL_ID_MAP.get(type);
        if (atomicLong == null) {
            AtomicLong value = new AtomicLong(0);
            AtomicLong old = LOCAL_ID_MAP.putIfAbsent(type, value);
            if (old != null) {
                atomicLong = old;
            }else {
                atomicLong = value;
            }
        }
        return atomicLong.addAndGet(1L);
    }

    private static Long createIdBySnowFlake(IDType type) {
        SnowFlakeIdWorker snowFlakeIdWorker = SNOW_FLAKE_ID_WORKER_MAP.get(type);
        if (snowFlakeIdWorker == null) {
            SnowFlakeIdWorker value = new SnowFlakeIdWorker(GlobalContant.SERVER_NO);
            SnowFlakeIdWorker old = SNOW_FLAKE_ID_WORKER_MAP.putIfAbsent(type, value);
            if (old != null) {
                snowFlakeIdWorker = old;
            }else {
                snowFlakeIdWorker = value;
            }
        }
        return snowFlakeIdWorker.nextId();
    }
}

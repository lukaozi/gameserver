package lucas.core.socket.net.session;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lushengkao vip8
 * 2018/11/8 20:57
 */
public class SessionIdGenerator {

    private static final AtomicLong ID_GEN = new AtomicLong(0);
    public static long getNetSessionId() {
        return ID_GEN.incrementAndGet();
    }
}

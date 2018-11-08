package lucas.net;

import lucas.net.session.NettySession;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lushengkao vip8
 * session管理
 * 2018/11/8 18:25
 */
@Component
public class NettySessionManager {

    private ConcurrentHashMap<Long, NettySession> nettySessions = new ConcurrentHashMap<>();

    public NettySession getSession(Long sessionId) {
        return nettySessions.get(sessionId);
    }

    public boolean addSession(NettySession session) {
        NettySession oldSession = nettySessions.putIfAbsent(session.getOwnerId(), session);
        return oldSession == null;
    }

    public void removeSession(long session) {
        nettySessions.remove(session);
    }
}

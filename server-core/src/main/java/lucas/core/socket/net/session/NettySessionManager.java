package lucas.core.socket.net.session;

import lucas.core.socket.net.session.GameSession;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lushengkao vip8
 * session管理
 * 2018/11/8 18:25
 */
@Component
public class NettySessionManager {

    private ConcurrentHashMap<Long, GameSession> nettySessions = new ConcurrentHashMap<>();

    public GameSession getSession(Long sessionId) {
        return nettySessions.get(sessionId);
    }

    public boolean addSession(GameSession session) {
        GameSession oldSession = nettySessions.putIfAbsent(session.getSessionId(), session);
        return oldSession == null;
    }

    public void removeSession(long session) {
        nettySessions.remove(session);
    }
}

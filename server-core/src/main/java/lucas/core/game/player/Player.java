package lucas.core.game.player;


import lucas.core.socket.net.session.GameSession;

/**
 * @author lushengkao vip8
 * 2018/11/9 14:19
 */
public class Player {

    private GameSession session;

    private String name;

    public long getPlayerId() {
        return session.getPlayerId();
    }

    public GameSession getSession() {
        return session;
    }

    public void setSession(GameSession session) {
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package lucas.core.game.player;


import lucas.core.game.bag.model.Bag;
import lucas.core.game.player.entity.PlayerEntity;
import lucas.core.socket.net.session.GameSession;

/**
 * @author lushengkao vip8
 * 2018/11/9 14:19
 */
public class Player {

    private PlayerEntity entity;

    private GameSession session;

    private long teamId;

    public Player(PlayerEntity playerEntity) {
        this.entity = playerEntity;
    }

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
        return entity.getName();
    }

    public void setName(String name) {
        entity.setName(name);
    }

    public void setEntity(PlayerEntity entity) {
        this.entity = entity;
    }

    public String getAccount() {
        return entity.getAccount();
    }

    public int getLevel() {
        return entity.getLevel();
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public Bag getBag() {
        return entity.getBag();
    }
}

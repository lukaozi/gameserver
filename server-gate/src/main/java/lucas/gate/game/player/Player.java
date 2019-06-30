package lucas.gate.game.player;


import lucas.gate.game.bag.model.Bag;
import lucas.gate.game.player.entity.PlayerEntity;
import lucas.gate.socket.net.session.GameSession;

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
        return entity.getId();
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

package lucas.core.game.player.service;

import lucas.core.game.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lushengkao vip8
 * 2018/11/9 14:20
 */
@Component
public class PlayerManager {

    private PlayerEntityService playerEntityService;

    @Autowired
    public void setPlayerEntityService(PlayerEntityService playerEntityService) {
        this.playerEntityService = playerEntityService;
    }

    private ConcurrentHashMap<Long, Player> playerData = new ConcurrentHashMap<>();

    public Player getPlayer(long playerId) {
        return playerData.get(playerId);
    }

    public boolean addPlayer(Player player) {
        Player old = playerData.putIfAbsent(player.getPlayerId(), player);
        return old == null;
    }

}

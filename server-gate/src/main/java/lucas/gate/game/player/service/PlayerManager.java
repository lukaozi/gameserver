package lucas.gate.game.player.service;

import lucas.gate.game.player.Player;
import lucas.gate.game.player.entity.PlayerEntity;
import lucas.mysql.annnotation.EntityServiceAnnotation;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lushengkao vip8
 * 2018/11/9 14:20
 */
@Component
public class PlayerManager {

    @EntityServiceAnnotation
    private PlayerEntityService playerEntityService;

    private ConcurrentHashMap<String,Long> account2Id = new ConcurrentHashMap<>();

    public Player getPlayer(long playerId) {
        PlayerEntity entity = playerEntityService.getEntity(playerId);
        return entity.getPlayer();
    }

    public boolean addAccount(String account,Long playerId) {
        Long oldAccount = account2Id.putIfAbsent(account, playerId);
        if (oldAccount != null) {
            return false;
        }
        return true;
    }

    public PlayerEntityService getPlayerEntityService() {
        return playerEntityService;
    }

    public Long getPlayerIdByAccount(String account) {
        return account2Id.get(account);
    }

    long id = 100;
    public long createPlayerId() {
        return ++id;
    }
}

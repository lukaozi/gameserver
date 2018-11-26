package lucas.core.game.player.controller;

import lucas.core.game.anno.GameController;
import lucas.core.game.anno.GameRequest;
import lucas.core.game.player.Player;
import lucas.core.game.player.entity.PlayerEntity;
import lucas.core.game.player.service.PlayerManager;
import lucas.core.packet.Req_Login;
import lucas.core.socket.net.session.GameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lushengkao vip8
 * 2018/11/8 20:25
 */

@Controller
@GameController
public class PlayerController {

    private PlayerManager playerManager;

    @Autowired
    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @GameRequest
    public void login(GameSession session,Req_Login req) {
        Player player;
        String account = req.getAccount();
        Long id = playerManager.getPlayerIdByAccount(account);
        if (id == null) {
            player = createPlayer(account,req.getName());
        }else {
            player = playerManager.getPlayer(id);
        }
        player.setSession(session);
        session.setPlayerId(player.getPlayerId());
        System.out.println(player.getName());
    }

    private Player createPlayer(String account,String name) {
        long playerId = playerManager.createPlayerId();
        PlayerEntity newEntity = new PlayerEntity(account,playerId);
        newEntity.setName(name);
        playerManager.getPlayerEntityService().insertEntity(newEntity);
        playerManager.addAccount(account,playerId);
        return newEntity.getPlayer();
    }
}

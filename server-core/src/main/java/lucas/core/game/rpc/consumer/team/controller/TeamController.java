package lucas.core.game.rpc.consumer.team.controller;

import lucas.core.game.anno.GameController;
import lucas.core.game.anno.GameRequest;
import lucas.core.game.player.Player;
import lucas.core.game.player.service.PlayerManager;
import lucas.core.game.rpc.consumer.team.service.TeamService;
import lucas.core.packet.Req_CreateTeam;
import lucas.core.socket.bootstarp.manager.SpringServiceManager;
import lucas.core.socket.net.session.GameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author lushengkao vip8
 * 2018/12/10 16:38
 */
@Controller
@GameController
public class TeamController {

    private TeamService teamService;

    @Autowired
    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    @GameRequest
    public void createTeam(GameSession session, Req_CreateTeam req) {
        long playerId = session.getPlayerId();
        PlayerManager playerManager = SpringServiceManager.getInstance().getPlayerManager();
        Player player = playerManager.getPlayer(playerId);
        if (player == null) {
            return;
        }
        teamService.createTeam(player);
    }
}

package lucas.gate.game.rpc.consumer.team.controller;

import lucas.gate.game.anno.GameController;
import lucas.gate.game.anno.GameRequest;
import lucas.gate.game.player.Player;
import lucas.gate.game.player.service.PlayerManager;
import lucas.gate.game.rpc.consumer.team.service.TeamService;
import lucas.gate.packet.Req_CreateTeam;
import lucas.gate.socket.bootstarp.manager.SpringServiceManager;
import lucas.gate.socket.net.session.GameSession;
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

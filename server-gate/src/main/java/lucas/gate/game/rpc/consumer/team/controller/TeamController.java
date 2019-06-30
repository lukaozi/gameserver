package lucas.gate.game.rpc.consumer.team.controller;

import lucas.gate.game.anno.GameController;
import lucas.gate.game.anno.GameRequest;
import lucas.gate.game.player.Player;
import lucas.gate.game.player.service.PlayerManager;
import lucas.gate.game.rpc.consumer.team.service.TeamService;
import lucas.gate.packet.Req_CreateTeam;
import lucas.gate.packet.Req_TeamList;
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

    private PlayerManager playerManager;

    @Autowired
    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Autowired
    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    @GameRequest
    public void createTeam(GameSession session, Req_CreateTeam req) {
        Player player = playerManager.getPlayer(session);
        if (player == null) return;
        teamService.createTeam(player);
    }

    @GameRequest
    public void createTeam(GameSession session, Req_TeamList req) {
        Player player = playerManager.getPlayer(session);
        if (player == null) return;
        teamService.listTeams(player);
    }
}

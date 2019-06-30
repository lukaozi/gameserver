package lucas.rpcapi.serverteam;

import lucas.rpcapi.serverteam.model.Team;
import lucas.rpcapi.serverteam.model.TeamPlayer;

import java.util.List;

/**
 * @author lushengkao vip8
 *
 * 队伍api
 * 2018/12/8 16:32
 */
public interface RpcTeamService {

    void createTeam(TeamPlayer player);

    List<Team> teamList();

    void applyTeam(TeamPlayer player, long teamId);

    void handleApplicant(TeamPlayer player, String applyAccount, boolean accept);

    void endTeam(TeamPlayer player);

}

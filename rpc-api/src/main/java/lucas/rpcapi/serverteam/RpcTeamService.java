package lucas.rpcapi.serverteam;

import lucas.rpcapi.serverteam.model.TeamPlayer;

/**
 * @author lushengkao vip8
 * 2018/12/8 16:32
 */
public interface RpcTeamService {

    void createTeam(TeamPlayer player);

    void applyTeam(TeamPlayer player, long teamId);

    void handleApplicant(TeamPlayer player, String applyAccount, boolean accept);

    void endTeam(TeamPlayer player);

}

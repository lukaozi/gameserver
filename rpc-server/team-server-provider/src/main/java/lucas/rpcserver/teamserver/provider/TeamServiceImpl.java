package lucas.rpcserver.teamserver.provider;

import lucas.common.util.LinkedLock;
import lucas.rpcserver.teamserver.RpcTeamService;
import lucas.rpcserver.teamserver.TeamDataManager;
import lucas.rpcserver.teamserver.model.Team;
import lucas.rpcserver.teamserver.model.TeamPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lushengkao vip8
 * 2018/12/7 17:10
 */
@Service
public class TeamServiceImpl implements RpcTeamService {

    private TeamDataManager teamDataManager;

    @Autowired
    public void setTeamDataManager(TeamDataManager teamDataManager) {
        this.teamDataManager = teamDataManager;
    }

    @Override
    public void createTeam(TeamPlayer player) {
        String account = player.getAccount();
        if (teamDataManager.inTeam(account)) {
            throw new RuntimeException("in team");
        }
        long teamId = teamDataManager.createTeamId();
        Team team =  new Team.builder().id(teamId).leader(account).build();
        Object accountLock = teamDataManager.getAccountLockObject(player.getPlayerId());
        Object teamLock = teamDataManager.getTeamLockObject(teamId);
        LinkedLock lock = createLinkedLock(accountLock, teamLock);
        try {
            lock.lock();
            if (teamDataManager.playerJoinTeam(account,teamId)) {
                teamDataManager.addTeam(team);
            }else {
                throw new RuntimeException("in team");
            }
        }finally {
            lock.unLock();
        }
    }

    @Override
    public void applyTeam(TeamPlayer player, long teamId) {

    }

    @Override
    public void handleApplicant(TeamPlayer player, String applyAccount,boolean accept) {

    }

    @Override
    public void endTeam(TeamPlayer player) {
        long teamId = player.getTeamId();
        Object teamLock = teamDataManager.getTeamLockObject(teamId);
        LinkedLock lock = createLinkedLock(teamLock);
        try {
            lock.lock();
            Team team = teamDataManager.getTeam(teamId);
            if (team == null) {
                return;
            }
            if (team.getLeaderAccount().equals(player.getAccount())) {
                teamDataManager.removeTeam(teamId);
            }
        }finally {
            lock.unLock();
        }
    }

    public LinkedLock createLinkedLock(Object... objects) {
        return LinkedLock.buildLock(objects);
    }
}

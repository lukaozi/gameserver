package lucas.rpcapi.serverteam;

import lucas.rpcapi.serverteam.model.Team;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lushengkao vip8
 * 2018/12/10 13:38
 */
@Component
public class TeamDataManager {


    private ConcurrentHashMap<Long, Team> teams = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String,Long> account2TeamId = new ConcurrentHashMap<>();

    private Object[] accountLocks = new Object[Runtime.getRuntime().availableProcessors() * 2];

    private Object[] teamLocks = new Object[Runtime.getRuntime().availableProcessors() * 2];

    @PostConstruct
    public void init() {
        for (int i = 0; i < accountLocks.length; i++) {
            accountLocks[i] = new Object();
        }
        for (int i = 0; i < teamLocks.length; i++) {
            teamLocks[i] = new Object();
        }
    }

    public boolean addTeam(Team team) {
        long id = team.getId();
        Team ifAbsent = teams.putIfAbsent(id, team);
        return ifAbsent == null;
    }

    public Team removeTeam(long id) {
        return teams.remove(id);
    }

    public boolean playerJoinTeam(String account,long teamId) {
        Long oldTeamId = account2TeamId.putIfAbsent(account, teamId);
        return oldTeamId == null;
    }

    public long playerOutTeam(String account) {
        return account2TeamId.remove(account);
    }

    public boolean inTeam(String account) {
        return account2TeamId.containsKey(account);
    }

    public Team getTeam(long teamId) {
        return teams.get(teamId);
    }

    public Object getAccountLockObject(long playerId) {
        return accountLocks[(int) (playerId % accountLocks.length)];
    }

    public Object getTeamLockObject(long teamId) {
        return teamLocks[(int) (teamId % teamLocks.length)];
    }

    public List<Team> getAllTeam() {
        return new LinkedList<>(teams.values());
    }
}

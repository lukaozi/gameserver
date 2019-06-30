package lucas.rpcapi.serverteam.model;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lushengkao vip8
 * 2018/12/10 13:27
 */
public class Team {

    private long id;

    private String leaderAccount;

    private ConcurrentHashMap<String, TeamPlayer> applicants;

    private ConcurrentHashMap<String, TeamPlayer> numbers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLeaderAccount() {
        return leaderAccount;
    }

    public void setLeaderAccount(String leaderAccount) {
        this.leaderAccount = leaderAccount;
    }

    public ConcurrentHashMap<String, TeamPlayer> getApplicants() {
        return applicants;
    }

    public void setApplicants(ConcurrentHashMap<String, TeamPlayer> applicants) {
        this.applicants = applicants;
    }

    public ConcurrentHashMap<String, TeamPlayer> getNumbers() {
        return numbers;
    }

    public void setNumbers(ConcurrentHashMap<String, TeamPlayer> numbers) {
        this.numbers = numbers;
    }

    public static final class builder {

        private long id;

        private String leaderAccount;

        public builder leader(String account) {
            this.leaderAccount = account;
            return this;
        }

        public builder id(long id) {
            this.id = id;
            return this;
        }

        public Team build() {
            Team result = new Team();
            result.applicants = new ConcurrentHashMap<>();
            result.numbers = new ConcurrentHashMap<>();
            result.id = this.id;
            result.leaderAccount = this.leaderAccount;
            return result;
        }
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", leaderAccount='" + leaderAccount + '\'' +
                '}';
    }
}

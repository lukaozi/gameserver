package lucas.gate.game.rpc.consumer.team.service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import lucas.gate.game.player.Player;
import lucas.mysql.utils.idgenerator.IDGenerator;
import lucas.mysql.utils.idgenerator.IDType;
import lucas.rpcapi.serverteam.RpcTeamService;
import lucas.rpcapi.serverteam.model.TeamPlayer;
import org.springframework.stereotype.Component;


/**
 * @author lushengkao vip8
 * 2018/12/8 16:28
 */
@Component
public class TeamService {

    private RpcTeamService rpcService;

    private ReferenceConfig<RpcTeamService> referenceConfig;

//    @PostConstruct
    private void connectRpc() {
        referenceConfig = new ReferenceConfig<>();
        ApplicationConfig application = new ApplicationConfig();
        application.setName("team-server-consumer");
        referenceConfig.setApplication(application);
        RegistryConfig registry = new RegistryConfig("127.0.0.1");
        registry.setPort(2181);
        registry.setProtocol("zookeeper");
        referenceConfig.setRegistry(registry);
        referenceConfig.setInterface(RpcTeamService.class);
        this.rpcService = referenceConfig.get();
    }

    public void createTeam(Player player) {
        long teamId = player.getTeamId();
        if (teamId != 0) {
            //TODO
            throw new RuntimeException("in team");
        }
        teamId = IDGenerator.createId(IDType.TEAM_ID);
        TeamPlayer teamPlayer = createTeamPlayer(player);
        teamPlayer.setTeamId(teamId);
        rpcService.createTeam(teamPlayer);
    }

    private TeamPlayer createTeamPlayer(Player player) {
        TeamPlayer teamPlayer = new TeamPlayer();
        teamPlayer.setAccount(player.getAccount());
        teamPlayer.setLevel(player.getLevel());
        teamPlayer.setPlayerId(player.getPlayerId());
        teamPlayer.setTeamId(player.getTeamId());
        return teamPlayer;
    }
}

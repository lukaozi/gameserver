package lucas.gate.game.rpc.consumer.team.service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.sun.istack.internal.NotNull;
import lucas.gate.game.player.Player;
import lucas.gate.packet.Res_TeamList;
import lucas.gate.socket.net.session.PacketSender;
import lucas.mysql.utils.idgenerator.IDGenerator;
import lucas.mysql.utils.idgenerator.IDType;
import lucas.rpcapi.serverteam.RpcTeamService;
import lucas.rpcapi.serverteam.model.Team;
import lucas.rpcapi.serverteam.model.TeamPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author lushengkao vip8
 * 2018/12/8 16:28
 */
@Component
public class TeamService {

    @Autowired
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
        referenceConfig.setProtocol("dubbo");
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

    public void listTeams(Player player) {
        List<Team> teams = rpcService.teamList();
        PacketSender.send(player,new Res_TeamList(teams));
        System.out.println("所有的队伍信息 ======" + teams);
    }
}

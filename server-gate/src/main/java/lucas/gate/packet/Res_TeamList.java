package lucas.gate.packet;

import lucas.gate.packet.packethandler.AbstractPacket;
import lucas.rpcapi.serverteam.model.Team;

import java.util.List;

/**
 * @author lushengkao vip8
 */
public class Res_TeamList implements AbstractPacket {
    private List<Team> teams;

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Res_TeamList(List<Team> teams) {
        this.teams = teams;
    }
}

package lucas.core.socket.task;

import lucas.core.game.player.service.PlayerManager;
import lucas.core.packet.packethandler.AbstractPacket;

/**
 * @author lushengkao vip8
 * 2018/11/9 14:46
 */
public class PlayerIoTask implements Runnable {

    private long playerId;

    private AbstractPacket packet;

    public PlayerIoTask(long playerId, AbstractPacket packet) {
        this.playerId = playerId;
        this.packet = packet;
    }

    @Override
    public void run() {
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public AbstractPacket getPacket() {
        return packet;
    }

    public void setPacket(AbstractPacket packet) {
        this.packet = packet;
    }
}

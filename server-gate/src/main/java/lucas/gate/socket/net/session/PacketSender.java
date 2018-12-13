package lucas.gate.socket.net.session;

import lucas.gate.game.player.Player;
import lucas.gate.packet.packethandler.AbstractPacket;
import lucas.gate.socket.bootstarp.manager.SpringServiceManager;
import lucas.gate.socket.net.message.AbstractNetMessage;
import lucas.gate.socket.net.protobuf.MessageHelper;

/**
 * @author lushengkao vip8
 * 2018/11/9 12:20
 */
public class PacketSender {

    public static void send(long PlayerId, AbstractPacket packet) {
        AbstractNetMessage message = MessageHelper.createMessageFromPacket(packet);
        Player player = SpringServiceManager.getInstance().getPlayerManager().getPlayer(PlayerId);
        player.getSession().write(message);
    }

    public static void send(Player player, AbstractPacket packet) {
        AbstractNetMessage message = MessageHelper.createMessageFromPacket(packet);
        player.getSession().write(message);
    }
}

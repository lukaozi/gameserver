package lucas.core.socket.net.session;

import lucas.core.game.player.Player;
import lucas.core.packet.packethandler.AbstractPacket;
import lucas.core.socket.bootstarp.manager.SpringServiceManager;
import lucas.core.socket.net.message.AbstractNetMessage;
import lucas.core.socket.net.protobuf.MessageHelper;

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

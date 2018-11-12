package lucas.core.socket.dispatcher;

import lucas.core.packet.packethandler.PacketHelper;
import lucas.core.socket.bootstarp.manager.SpringBeanManager;
import lucas.core.socket.executors.ArrayExecutor;
import lucas.core.packet.packethandler.PacketType;
import lucas.core.socket.net.message.INetMessage;
import lucas.core.packet.packethandler.AbstractPacket;
import lucas.core.socket.net.protobuf.MessageHelper;
import lucas.core.socket.net.session.GameSession;
import lucas.core.socket.task.PlayerIoTask;

/**
 * @author lushengkao vip8
 * 2018/11/8 15:09
 */
public class GamePacketDispatcher implements IDispatcher {

    private ArrayExecutor arrayExecutor;

    public GamePacketDispatcher() {
        arrayExecutor = ArrayExecutor.build("gameCore",Runtime.getRuntime().availableProcessors() * 2);
    }

    @Override
    public void dispatcher(GameSession session, INetMessage message) {
        byte[] bytes = message.getBody().getBytes();
        int command = message.getHead().getCommand();
        PacketHelper packetHelper = SpringBeanManager.getInstance().getPacketHelper();
        Class<? extends AbstractPacket> clazz = packetHelper.getClazz(command);
        AbstractPacket packet = MessageHelper.deserialize(bytes, clazz);
        long playerId = session.getPlayerId();
        long runId = playerId == 0 ? session.getSessionId() : playerId;
        PlayerIoTask task = new PlayerIoTask(runId, packet,session);
        arrayExecutor.submit(task,task.getPlayerId());
    }
}

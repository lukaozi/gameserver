package lucas.core.socket.dispatcher;

import lucas.core.socket.executors.ArrayExecutor;
import lucas.core.packet.PacketType;
import lucas.core.socket.net.message.INetMessage;
import lucas.core.packet.AbstractPacket;
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
        Class<? extends AbstractPacket> clazz = PacketType.getClazz(command);
        AbstractPacket packet = MessageHelper.deserialize(bytes, clazz);
        long playerId = session.getPlayerId();
        long runId = playerId == 0 ? session.getSessionId() : playerId;
        PlayerIoTask task = new PlayerIoTask(runId, packet);
        arrayExecutor.submit(task,task.getPlayerId());
    }
}

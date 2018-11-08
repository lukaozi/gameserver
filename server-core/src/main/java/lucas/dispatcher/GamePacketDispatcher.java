package lucas.dispatcher;

import lucas.executors.ArrayExecutor;
import lucas.net.PacketType;
import lucas.net.message.INetMessage;
import lucas.net.packet.AbstractPacket;
import lucas.net.protobuf.RPCProtostuffHelper;
import lucas.net.session.NettySession;

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
    public void dispatcher(NettySession session, INetMessage message) {
        byte[] bytes = message.getBody().getBytes();
        int command = message.getHead().getCommand();
        Class<? extends AbstractPacket> clazz = PacketType.getClazz(command);
        AbstractPacket packet = RPCProtostuffHelper.deserialize(bytes, clazz);
    }
}

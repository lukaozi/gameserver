package lucas.gate.socket.task;

import lucas.gate.packet.packethandler.AbstractPacket;
import lucas.gate.packet.packethandler.PacketHandlerAdapter;
import lucas.gate.packet.packethandler.PacketHandlerService;
import lucas.gate.socket.bootstarp.manager.SpringServiceManager;
import lucas.gate.socket.net.session.GameSession;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lushengkao vip8
 * 2018/11/9 14:46
 */
public class PlayerIoTask implements Runnable {

    private long playerId;

    private AbstractPacket packet;

    private GameSession gameSession;

    public PlayerIoTask(long playerId, AbstractPacket packet, GameSession session) {
        this.playerId = playerId;
        this.packet = packet;
        this.gameSession = session;
    }

    @Override
    public void run() {
        try {
            PacketHandlerService handlerService = SpringServiceManager.getInstance().getPacketHandlerService();
            ConcurrentHashMap<Class, PacketHandlerAdapter> handlerAdapters = handlerService.getHandlerAdapters();
            PacketHandlerAdapter adapter = handlerAdapters.get(packet.getClass());
            Object bean = adapter.getBean();
            Method method = adapter.getMethod();
            method.invoke(bean,gameSession,packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

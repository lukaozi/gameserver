package lucas.gate.socket.net.session;

import io.netty.channel.Channel;
import lucas.common.log.Loggers;
import lucas.gate.game.player.Player;
import lucas.gate.game.player.service.PlayerManager;
import lucas.gate.socket.bootstarp.manager.SpringServiceManager;
import lucas.gate.socket.net.message.MessageException;
import lucas.gate.socket.net.message.AbstractNetMessage;
import org.slf4j.Logger;

/**
 * @author lushengkao vip8
 * netty会话持有的session
 * 2018/10/11 18:15
 */
public class GameSession implements ISession {

    private final static Logger LOGGER = Loggers.NET_ERROR_LOGGER;

    private volatile Channel channel;

    private long sessionId;

    private volatile long playerId;

    public GameSession(Channel channel, long sessionId) {
        this.channel = channel;
        this.sessionId = sessionId;
    }

    public boolean isConnected() {
        if (channel == null) {
            return false;
        }
        return channel.isActive();
    }

    public void write(AbstractNetMessage message) {
        if (message == null) {
            throw new MessageException("发送的信息为空");
        }
        if (channel == null) {
            throw new MessageException("channel为空");
        }
        try {
            channel.writeAndFlush(message);
        } catch (Exception e) {
            LOGGER.info("session message exception",e);
            throw new MessageException(e);
        }
    }

    public void write(byte[] message) {
        if (channel == null) {
            throw new MessageException("channel为空");
        }
        try {
            channel.writeAndFlush(message);
        } catch (Exception e) {
            LOGGER.info("session message exception",e);
            throw new MessageException(e);
        }
    }

    public void close(boolean immediately) {
        if (channel != null) {
            channel.close();
        }
    }

    public long getSessionId() {
        return sessionId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public Player getPlayer() {
        PlayerManager playerManager = SpringServiceManager.getInstance().getPlayerManager();
        return playerManager.getPlayer(playerId);
    }
}

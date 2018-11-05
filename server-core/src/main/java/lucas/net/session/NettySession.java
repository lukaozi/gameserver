package lucas.net.session;

import io.netty.channel.Channel;
import lucas.common.log.Loggers;
import lucas.net.MessageException;
import lucas.net.message.INetMessage;
import org.slf4j.Logger;

/**
 * @author lushengkao vip8
 * netty会话持有的session
 * 2018/10/11 18:15
 */
public class NettySession implements ISession {

    private final static Logger LOGGER = Loggers.NET_ERROR_LOGGER;

    private volatile Channel channel;

    private long ownerId;

    public NettySession(Channel channel, long ownerId) {
        this.channel = channel;
        this.ownerId = ownerId;
    }

    public boolean isConnected() {
        if (channel == null) {
            return false;
        }
        return channel.isActive();
    }

    public void write(INetMessage message) {
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

    public long getOwnerId() {
        return ownerId;
    }
}

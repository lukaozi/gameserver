package lucas.net.session;


import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

/**
 * @author lushengkao vip8
 * 2018/11/8 20:49
 */
@Service
public class NettySessionBuilder implements ISessionBuilder{

    @Override
    public NettySession buildSession(Channel channel) {
        long sessionId = SessionIdGenerator.getNetSessionId();
        NettySession session = new NettySession(channel,sessionId);
        channel.attr(SessionContant.CHANNEL_SESSION_ID).set(sessionId);
        return session;
    }
}

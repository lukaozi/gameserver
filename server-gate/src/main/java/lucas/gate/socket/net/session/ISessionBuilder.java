package lucas.gate.socket.net.session;

import io.netty.channel.Channel;

/**
 * @author lushengkao vip8
 * 2018/11/8 20:50
 */
public interface ISessionBuilder {

    ISession buildSession(Channel channel);
}

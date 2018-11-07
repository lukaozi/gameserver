package lucas.net.server.channelhandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * @author lushengkao vip8
 * 2018/11/7 16:37
 */
public class ProtoBufTcpChannelInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

    }
}

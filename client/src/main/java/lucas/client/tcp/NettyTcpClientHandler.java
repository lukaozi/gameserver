package lucas.client.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lucas.channelHandler.GamePacketHandler;
import lucas.dispatcher.GamePacketDispatcher;
import lucas.net.protobuf.NetMessageDecoder;
import lucas.net.protobuf.NetMessageEncoder;
import lucas.net.protobuf.PacketToMessageEncoder;

/**
 * @author lushengkao vip8
 * 添加客户端处理器
 * 2018/10/19 16:20
 */
public class NettyTcpClientHandler extends ChannelInitializer {

    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,2,4,0,0))
                .addLast(new NetMessageDecoder())
                .addLast(new PacketToMessageEncoder())
                .addLast(new NetMessageEncoder())
                .addLast(new IdleStateHandler(60,60,60))
                .addLast(new GamePacketHandler(new GamePacketDispatcher()));
    }
}

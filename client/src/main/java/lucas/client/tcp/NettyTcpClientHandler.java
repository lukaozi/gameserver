package lucas.client.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lucas.gate.socket.channelHandler.GamePacketHandler;
import lucas.gate.socket.dispatcher.GamePacketDispatcher;
import lucas.gate.socket.net.protobuf.NetMessageDecoder;
import lucas.gate.socket.net.protobuf.NetMessageEncoder;

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
                .addLast(new NetMessageEncoder())
                .addLast(new GamePacketHandler(new GamePacketDispatcher()));
    }
}

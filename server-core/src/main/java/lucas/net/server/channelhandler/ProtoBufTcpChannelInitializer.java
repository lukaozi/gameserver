package lucas.net.server.channelhandler;

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
 *  LengthFieldBasedFrameDecoder 使用
 *  head+messagelength+serial+body
 *  参数maxFrameLength 为数据帧最大长度
 *  参数lengthFieldOffset为version长度表示 从第几个字段开始读取长度，表示同意为head的长度
 *  参数lengthFieldLength表示占用了多少个字节数 具体可查看LengthFieldBasedFrameDecoder的getUnadjustedFrameLength方法
 *  参数lengthAdjustment表示还需要拓展长度，具体表示为serial的长度
 *  参数initialBytesToStrip表示 传递给下个coder的时候跳过多少字节 如果从0开始为 head+messagelength+serial+body全部给下个coder
 */
public class ProtoBufTcpChannelInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,2,4,0,0))
                .addLast(new NetMessageDecoder())
                .addLast(new PacketToMessageEncoder())
                .addLast(new NetMessageEncoder())
                .addLast(new IdleStateHandler(60,60,60))
                .addLast(new GamePacketHandler(new GamePacketDispatcher()));
    }
}

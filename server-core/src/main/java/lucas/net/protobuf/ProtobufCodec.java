package lucas.net.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @author lushengkao vip8
 * 2018/11/6 10:15
 */
public class ProtobufCodec extends MessageToMessageCodec<ByteBuf,ProtoBufNetMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ProtoBufNetMessage msg, List<Object> out) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(praseMessage(msg));
    }

    private Object praseMessage(ByteBuf msg) {
        return null;
    }
}

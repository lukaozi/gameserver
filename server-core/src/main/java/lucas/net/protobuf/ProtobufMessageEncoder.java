package lucas.net.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author lushengkao vip8
 * 2018/11/7 16:31
 */
public class ProtobufMessageEncoder extends MessageToMessageEncoder<ProtoBufNetMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ProtoBufNetMessage msg, List<Object> out) throws Exception {

    }
}

package lucas.core.socket.net.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lucas.core.socket.net.message.AbstractNetMessage;
import lucas.core.socket.net.message.NetMessageBody;
import lucas.core.socket.net.message.NetMessageHead;

import java.util.List;

/**
 * @author lushengkao vip8
 * 2018/11/7 16:31
 */
public class NetMessageEncoder extends MessageToMessageEncoder<AbstractNetMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractNetMessage msg, List<Object> out) {
        ByteBuf byteBuf = Unpooled.buffer(256);
        //编写head
        NetMessageHead netMessageHead = msg.getHead();
        byteBuf.writeShort(netMessageHead.getHead());
        //长度
        byteBuf.writeByte(netMessageHead.getVersion());
        byteBuf.writeInt(0);
        byteBuf.writeInt(netMessageHead.getSerial());
        byteBuf.writeInt(netMessageHead.getCommand());
        //编写body
        NetMessageBody netMessageBody = msg.getBody();
        byteBuf.writeBytes(netMessageBody.getBytes());
        //重新设置长度
        int skip = 7;
        int length = byteBuf.readableBytes() - skip;
        byteBuf.setInt(3, length);
        out.add(byteBuf);
    }
}

package lucas.net.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lucas.GlobalContant;
import lucas.net.PacketType;
import lucas.net.message.NetMessageHead;
import lucas.net.packet.AbstractPacket;

import java.util.List;

/**
 * @author lushengkao vip8
 * 2018/11/8 12:23
 */
public class PacketToMessageEncoder extends MessageToMessageEncoder<AbstractPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractPacket msg, List<Object> out) {
        ProtoBufNetMessage protoBufNetMessage = new ProtoBufNetMessage();
        byte[] serialize = RPCProtostuffHelper.serialize(msg);
        protoBufNetMessage.getBody().setBytes(serialize);
        NetMessageHead head = protoBufNetMessage.getHead();
        head.setVersion(GlobalContant.VERSION);
        head.setCommand(PacketType.getCommand(msg.getClass()));
        out.add(protoBufNetMessage);
    }
}

package lucas.channelHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lucas.dispatcher.IDispatcher;
import lucas.net.PacketType;
import lucas.net.packet.AbstractPacket;
import lucas.net.protobuf.ProtoBufNetMessage;
import lucas.net.protobuf.RPCProtostuffHelper;
import lucas.net.session.NettySession;

/**
 * @author lushengkao vip8
 * 2018/11/8 14:57
 */
public class GamePacketHandler extends DispatcherChannelHandler {

    public GamePacketHandler(IDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        ProtoBufNetMessage message = (ProtoBufNetMessage) msg;
        getDispatcher().dispatcher(getSession(ctx.channel()),message);
    }

    private NettySession getSession(Channel channel) {
        return null;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

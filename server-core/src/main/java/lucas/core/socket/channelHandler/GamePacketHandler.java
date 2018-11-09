package lucas.core.socket.channelHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lucas.core.socket.bootstarp.manager.SpringBeanManager;
import lucas.core.socket.bootstarp.manager.SpringServiceManager;
import lucas.core.socket.dispatcher.IDispatcher;
import lucas.core.socket.net.protobuf.ProtoBufNetMessage;
import lucas.core.socket.net.session.GameSession;
import lucas.core.socket.net.session.NettySessionBuilder;
import lucas.core.socket.net.session.SessionContant;

/**
 * @author lushengkao vip8
 * 2018/11/8 14:57
 */
public class GamePacketHandler extends DispatcherChannelHandler {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        SpringBeanManager springBeanManager = SpringBeanManager.getInstance();
        NettySessionBuilder nettySessionBuilder = springBeanManager.getNettySessionBuilder();
        GameSession session = nettySessionBuilder.buildSession(ctx.channel());
        SpringServiceManager springServiceManager = SpringServiceManager.getInstance();
        boolean success = springServiceManager.getNettySessionManager().addSession(session);
        if (success) {
            //todo 成功
        }
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    public GamePacketHandler(IDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        ProtoBufNetMessage message = (ProtoBufNetMessage) msg;
        getDispatcher().dispatcher(getSession(ctx.channel()),message);
    }

    private GameSession getSession(Channel channel) {
        Long sessionId = channel.attr(SessionContant.CHANNEL_SESSION_ID).get();
        SpringServiceManager springServiceManager = SpringServiceManager.getInstance();
        return springServiceManager.getNettySessionManager().getSession(sessionId);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

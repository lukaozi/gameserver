package lucas.channelHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lucas.core.bootstarp.manager.SpringBeanManager;
import lucas.core.bootstarp.manager.SpringServiceManager;
import lucas.dispatcher.IDispatcher;
import lucas.net.protobuf.ProtoBufNetMessage;
import lucas.net.session.NettySession;
import lucas.net.session.NettySessionBuilder;
import lucas.net.session.SessionContant;

/**
 * @author lushengkao vip8
 * 2018/11/8 14:57
 */
public class GamePacketHandler extends DispatcherChannelHandler {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        SpringBeanManager springBeanManager = SpringBeanManager.getInstance();
        NettySessionBuilder nettySessionBuilder = springBeanManager.getNettySessionBuilder();
        NettySession session = nettySessionBuilder.buildSession(ctx.channel());
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

    private NettySession getSession(Channel channel) {
        Long sessionId = channel.attr(SessionContant.CHANNEL_SESSION_ID).get();
        SpringServiceManager springServiceManager = SpringServiceManager.getInstance();
        return springServiceManager.getNettySessionManager().getSession(sessionId);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

package lucas.gate.socket.channelHandler;

import io.netty.channel.SimpleChannelInboundHandler;
import lucas.gate.socket.dispatcher.IDispatcher;

/**
 * @author lushengkao vip8
 * 2018/11/2 15:47
 */
public abstract class DispatcherChannelHandler extends SimpleChannelInboundHandler {

    private IDispatcher dispatcher;

    public IDispatcher getDispatcher() {
        return dispatcher;
    }

    public DispatcherChannelHandler(IDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}

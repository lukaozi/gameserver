package lucas.channelHandler;

import io.netty.channel.SimpleChannelInboundHandler;
import lucas.dispatcher.IDispatcher;

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

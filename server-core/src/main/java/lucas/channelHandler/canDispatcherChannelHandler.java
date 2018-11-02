package lucas.channelHandler;

import io.netty.channel.ChannelHandler;
import lucas.dispatcher.IDispatcher;

/**
 * @author lushengkao vip8
 * 2018/11/2 15:47
 */
public interface canDispatcherChannelHandler extends ChannelHandler {

    IDispatcher getDispatcher();
}

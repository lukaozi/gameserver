package lucas.net.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import lucas.common.log.Loggers;
import lucas.common.util.NamedThreadFactory;
import org.slf4j.Logger;

/**
 * @author lushengkao vip8
 * 2018/10/11 20:10
 */
public class NettyTcpSocketServerService implements IServerService {

    private static final Logger LOGGER = Loggers.SERVER_LOGGER;
    /**
     * tcp socket 的channel handler
     */
    private ChannelInitializer channelInitializer;

    /**
     * 关闭返回
     */
    private ChannelFuture serverChannelFutrue;

    private NamedThreadFactory bossNamedThreadFactory;

    private NamedThreadFactory workerNamedThreadFactory;

    @Override
    public void startServer() {

    }

    @Override
    public void stopServer() {

    }
}

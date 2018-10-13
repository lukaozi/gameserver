package lucas.net.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lucas.common.log.Loggers;
import lucas.common.util.NamedThreadFactory;
import org.slf4j.Logger;


/**
 * @author lushengkao vip8
 * 2018/10/11 20:10
 */
public class NettyTcpSocketServerService implements IServerService {

    //服务端口号 后面应该改为配置形式
    private static final int SERVER_PORT = 8801;

    private static final Logger LOGGER = Loggers.SERVER_LOGGER;
    /**
     * tcp socket 的channel handler
     */
    private ChannelInitializer channelInitializer;

    /**
     * 关闭返回
     */
    private ChannelFuture serverChannelFutrue;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    //初始化逻辑
    public NettyTcpSocketServerService() {
        NamedThreadFactory bossNamedThreadFactory = new NamedThreadFactory("netty-boss");
        NamedThreadFactory workerNamedThreadFactory = new NamedThreadFactory("netty-worker");
        bossGroup = new NioEventLoopGroup(1, bossNamedThreadFactory);
        //这里的0默认cpu core数量的两倍
        workerGroup = new NioEventLoopGroup(0, workerNamedThreadFactory);
        channelInitializer = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel channel) {
                ChannelPipeline pipeline = channel.pipeline();
                //TODO 添加一下处理器
            }
        };
    }

    @Override
    public void startServer() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                //端口关闭后的可重用性
                .childOption(ChannelOption.SO_REUSEADDR, true)
                //tcp接收缓存
                .childOption(ChannelOption.SO_RCVBUF, 65536)
                //tcp发送缓存
                .childOption(ChannelOption.SO_SNDBUF, 65536)
                //延时算法
                .childOption(ChannelOption.TCP_NODELAY, true)
                //是否保活
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // heap buf 's better
                .childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(false))
                .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(channelInitializer);
        try {
            serverChannelFutrue = serverBootstrap.bind(SERVER_PORT).sync();
            serverChannelFutrue.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
        } catch (InterruptedException e) {
            LOGGER.error("端口" + SERVER_PORT + "服务启动失败");
            e.printStackTrace();
        }


    }

    @Override
    public void stopServer() {
        if (serverChannelFutrue != null) {
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }
            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
        }
    }
}

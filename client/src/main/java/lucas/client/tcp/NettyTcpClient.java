package lucas.client.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author lushengkao vip8
 * tcp客户端
 * 2018/10/19 16:18
 */
public class NettyTcpClient {

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 8001;

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    // 使用NioSocketChannel来作为连接用的channel类
                    .channel(NioSocketChannel.class)
                    // 绑定连接端口和host信息
                    .remoteAddress(new InetSocketAddress(HOST, PORT))
                    .handler(new NettyTcpClientHandler());

            ChannelFuture cf = b.connect().sync();
            cf.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
        }
    }
}

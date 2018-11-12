package lucas.client.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

/**
 * @author lushengkao vip8
 * tcp客户端
 * 2018/10/19 16:18
 */
public class NettyTcpClient {

    //确保已经链接上
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 8001;

    private volatile Channel clientChannel;

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
            Channel channel = cf.channel();
            clientChannel = channel;
            countDownLatch.countDown();
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
        }
    }

    public Channel getClientChannel() {
        return clientChannel;
    }

    public void clientWait() throws InterruptedException {
        countDownLatch.await();
    }
}

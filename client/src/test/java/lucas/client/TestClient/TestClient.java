//package lucas.client.TestClient;
//
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFuture;
//import lucas.client.tcp.NettyTcpClient;
//import lucas.gate.packet.Req_Login;
//import lucas.gate.socket.net.message.AbstractNetMessage;
//import lucas.gate.socket.net.protobuf.MessageHelper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.concurrent.ExecutionException;
//
//
///**
// * @author lushengkao vip8
// * 2018/11/12 14:50
// */
//
//@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
//@ContextConfiguration(locations={"classpath:server/applicationContext.xml"}) //加载配置文件
//public class TestClient {
//
//    private NettyTcpClient client = new NettyTcpClient();
//
//    @Test
//    public void t1() throws Exception {
//        Runnable runnable = () -> {
//            try {
//                sendMessage();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        };
//        Thread thread = new Thread(runnable);
//        thread.start();
//        client.start();
//    }
//
//    private void sendMessage() throws InterruptedException, ExecutionException {
//        client.clientWait();
//        Channel channel = client.getClientChannel();
//        Req_Login msg = new Req_Login();
//        msg.setAccount("lushengkao");
//        msg.setName("考神");
//        AbstractNetMessage message = MessageHelper.createMessageFromPacket(msg);
//        ChannelFuture channelFuture = channel.writeAndFlush(message);
//        channelFuture.get();
//    }
//
//}

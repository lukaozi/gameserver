package lucas.client.TestClient;

import io.netty.channel.Channel;
import lucas.client.tcp.NettyTcpClient;
import lucas.core.packet.Req_Login;
import lucas.core.socket.net.message.AbstractNetMessage;
import lucas.core.socket.net.protobuf.MessageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author lushengkao vip8
 * 2018/11/12 14:50
 */

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:applicationContext.xml"}) //加载配置文件
public class TestClient {

    private NettyTcpClient client = new NettyTcpClient();

    @Test
    public void t1() throws Exception {
        Runnable runnable = () -> {
            try {
                sendMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        client.start();
    }

    private void sendMessage() throws InterruptedException {
        client.clientWait();
        Channel channel = client.getClientChannel();
        Req_Login msg = new Req_Login();
        msg.setAccount("lushengkao");
        AbstractNetMessage message = MessageHelper.createMessageFromPacket(msg);
        channel.writeAndFlush(message);
    }

}

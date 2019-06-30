package lucas.client.teamtest;


import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lucas.client.tcp.NettyTcpClient;
import lucas.gate.packet.Req_CreateTeam;
import lucas.gate.packet.Req_Login;
import lucas.gate.packet.Req_TeamList;
import lucas.gate.packet.packethandler.AbstractPacket;
import lucas.gate.socket.net.message.AbstractNetMessage;
import lucas.gate.socket.net.protobuf.MessageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutionException;

/**
 * @author lushengkao vip8
 */
@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations = {"classpath:server/applicationContext.xml"}) //加载配置文件
public class TeamTest {

    private NettyTcpClient client = new NettyTcpClient();

    @Test
    public void request() throws Exception {
        Runnable runnable = () -> {
            try {
                client.clientWait();
                sendMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        client.start();
    }

    private void sendMessage() {
        //登录
        login();
        //创建队伍
        createTeam();
        //队伍列表
        listTeams();
    }

    private void login() {
        sendPacket(new Req_Login("a5112418","shengkao"));
    }

    private void createTeam() {
        sendPacket(new Req_CreateTeam());
    }

    private void listTeams() {
        sendPacket(new Req_TeamList());
    }

    private void sendPacket(AbstractPacket packet) {
        try {
            Channel channel = client.getClientChannel();
            AbstractNetMessage message = MessageHelper.createMessageFromPacket(packet);
            ChannelFuture channelFuture = channel.writeAndFlush(message);
            channelFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}

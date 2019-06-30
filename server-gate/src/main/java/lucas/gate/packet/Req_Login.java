package lucas.gate.packet;

import lucas.gate.packet.packethandler.AbstractPacket;

/**
 * @author lushengkao vip8
 * 登陆请求
 * 2018/11/7 16:24
 */
public class Req_Login implements AbstractPacket {

    private String account;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Req_Login(String account, String name) {
        this.account = account;
        this.name = name;
    }
}

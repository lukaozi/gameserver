package lucas.net.packet;

/**
 * @author lushengkao vip8
 * 登陆请求
 * 2018/11/7 16:24
 */
public class Req_Login implements AbstractPacket {

    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}

package lucas.gate.socket.net.message;

/**
 * @author lushengkao vip8
 * 2018/11/5 14:55
 */
public class AbstractNetMessage implements INetMessage {

    private NetMessageHead head;

    private NetMessageBody body;

    public AbstractNetMessage(NetMessageHead head, NetMessageBody body) {
        this.head = head;
        this.body = body;
    }

    public AbstractNetMessage() {
        this.head = new NetMessageHead();
        this.body = new NetMessageBody();
    }

    public NetMessageHead getHead() {
        return head;
    }

    public NetMessageBody getBody() {
        return body;
    }

    public void setHead(NetMessageHead head) {
        this.head = head;
    }

    public void setBody(NetMessageBody body) {
        this.body = body;
    }
}

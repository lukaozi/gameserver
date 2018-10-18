package lucas.net.message;

public interface INetMessage {

    /**
     * 获取消息头
     * @return
     */
    NetMessageHead getHead();

    /**
     * 获取消息体
     * @return
     */
    NetMessageBody getBody();
}

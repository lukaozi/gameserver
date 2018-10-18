package lucas.net.session;

import lucas.net.message.INetMessage;

public interface ISession {

    /**
     * session是否存在
     * @return
     */
    boolean isConnected();

    /**
     * 写消息
     * @param message
     */
    void write(INetMessage message);

    /**
     * 直接写字节
     * @param message
     */
    void write(byte[] message);

    /**
     * 关闭session
     * @param immediately
     */
    void close(boolean immediately);
}

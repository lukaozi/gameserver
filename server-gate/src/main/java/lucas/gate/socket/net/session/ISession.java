package lucas.gate.socket.net.session;

import lucas.gate.socket.net.message.AbstractNetMessage;

public interface ISession {

    /**
     * session是否存在
     * @return
     */
    boolean isConnected();

    /**
     * 写消息
     * @param object
     */
    void write(AbstractNetMessage object);

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

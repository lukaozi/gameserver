package lucas.net.session;

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
    void write(Object object);

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

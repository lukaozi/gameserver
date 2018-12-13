package lucas.gate.socket.net.message;

/**
 * @author lushengkao vip8
 * 网络io异常
 * 2018/11/5 14:24
 */
public class MessageException extends RuntimeException {

    public MessageException(String e) {
        super(e);
    }

    public MessageException(Exception e) {
        super(e);
    }
}

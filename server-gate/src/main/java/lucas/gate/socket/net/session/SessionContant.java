package lucas.gate.socket.net.session;

import io.netty.util.AttributeKey;

/**
 * @author lushengkao vip8
 * 2018/11/8 20:51
 */
public class SessionContant {

    //session 标识
    public static final AttributeKey<Long> CHANNEL_SESSION_ID = AttributeKey.valueOf("channel_session_id");
}

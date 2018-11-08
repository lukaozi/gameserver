package lucas.dispatcher;

import lucas.net.message.INetMessage;
import lucas.net.session.NettySession;


public interface IDispatcher {

    void dispatcher(NettySession session, INetMessage message);
}

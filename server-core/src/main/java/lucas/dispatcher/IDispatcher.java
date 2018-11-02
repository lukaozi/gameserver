package lucas.dispatcher;

import lucas.executors.ArrayExecutor;
import lucas.net.message.INetMessage;
import lucas.net.session.NettySession;


public interface IDispatcher {

    ArrayExecutor getExecutors();

    void dispatcher(NettySession session, INetMessage message);
}

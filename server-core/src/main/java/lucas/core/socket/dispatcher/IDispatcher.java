package lucas.core.socket.dispatcher;

import lucas.core.socket.net.message.INetMessage;
import lucas.core.socket.net.session.GameSession;


public interface IDispatcher {

    void dispatcher(GameSession session, INetMessage message);
}

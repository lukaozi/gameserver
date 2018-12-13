package lucas.gate.socket.dispatcher;

import lucas.gate.socket.net.message.INetMessage;
import lucas.gate.socket.net.session.GameSession;


public interface IDispatcher {

    void dispatcher(GameSession session, INetMessage message);
}

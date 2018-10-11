package lucas.net.session;

import lucas.net.message.INetMessage;

public interface ISession {

    boolean isConnected();

    void write(INetMessage message);

    void write(byte[] message);

    void close(boolean immediately);
}

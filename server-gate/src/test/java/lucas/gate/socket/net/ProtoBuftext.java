package lucas.gate.socket.net;

import lucas.gate.socket.net.protobuf.MessageHelper;
import org.junit.Test;

/**
 * @author lushengkao vip8
 */
public class ProtoBuftext {

    @Test
    public void t1() {
        Text22 text22 = new Text22();
        text22.setName("lucas");
        text22.setAge("123");
        byte[] serialize = MessageHelper.serialize(text22);
        Text22 text221 = MessageHelper.deserialize(serialize, Text22.class);
        System.out.println(text221);
    }
}

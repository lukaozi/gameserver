package lucas.core.socket.net.protobuf;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lucas.common.GlobalContant;
import lucas.core.packet.packethandler.PacketHelper;
import lucas.core.socket.bootstarp.manager.SpringBeanManager;
import lucas.core.socket.net.message.AbstractNetMessage;
import lucas.core.socket.net.message.NetMessageHead;
import lucas.core.packet.packethandler.AbstractPacket;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lushengkao vip8
 * 用于rpc传输
 * 2018/11/5 21:25
 */
public class MessageHelper {

    private static final Map<Class<?>, Schema<?>> CACHED_SCHEMA = new ConcurrentHashMap<>();

    private static Objenesis objenesis = new ObjenesisStd(true);

    @SuppressWarnings("unchecked")
    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) CACHED_SCHEMA.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            CACHED_SCHEMA.put(cls, schema);
        }
        return schema;
    }

    /**
     * 序列化（对象 -> 字节数组）
     */
    @SuppressWarnings("unchecked")
    public static <T> byte[] serialize(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(cls);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }

    /**
     * 反序列化（字节数组 -> 对象）
     */
    public static <T> T deserialize(byte[] data, Class<T> cls) {
        try {
            T message = objenesis.newInstance(cls);
            Schema<T> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * 生成对象
     */
    public static <T> T newInstance(Class<T> cls) {
        try {
            return objenesis.newInstance(cls);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static AbstractNetMessage createMessageFromPacket(AbstractPacket packet) {
        AbstractNetMessage protoBufNetMessage = new AbstractNetMessage();
        byte[] serialize = MessageHelper.serialize(packet);
        protoBufNetMessage.getBody().setBytes(serialize);
        NetMessageHead head = protoBufNetMessage.getHead();
        head.setVersion(GlobalContant.VERSION);
        Class<? extends AbstractPacket> packetClass = packet.getClass();
        PacketHelper packetHelper = SpringBeanManager.getInstance().getPacketHelper();
        int command = packetHelper.getCommand(packetClass);
        head.setCommand(command);
        return protoBufNetMessage;
    }
}

package lucas.core.packet.packethandler;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lushengkao vip8
 * 2018/11/12 15:52
 */
@Component
public class PacketHelper {

    private static final ConcurrentHashMap<Integer,Class<? extends AbstractPacket>> id2Class = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<Class<? extends AbstractPacket>,Integer> class2id = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        for (PacketType value : PacketType.values()) {
            id2Class.put(value.getCommand(),value.getClazz());
            class2id.put(value.getClazz(),value.getCommand());
        }
    }

    public Integer getCommand(Class<? extends AbstractPacket> clazz) {
        return class2id.get(clazz);
    }

    public Class<? extends AbstractPacket> getClazz(Integer command) {
        return id2Class.get(command);
    }
}

package lucas.core.packet.packethandler;

import lucas.core.game.anno.GameController;
import lucas.core.game.anno.GameRequest;
import lucas.core.game.player.Player;
import lucas.core.socket.net.session.GameSession;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lushengkao vip8
 * 2018/11/9 15:10
 */
@Service
public class PacketHandlerService {

    private ConcurrentHashMap<Class,PacketHandlerAdapter> handlerAdapters = new ConcurrentHashMap<>();

    Object register(Object bean) {
        GameController annotation = bean.getClass().getAnnotation(GameController.class);
        if (annotation == null) {
            return bean;
        }
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            GameRequest request = declaredMethod.getAnnotation(GameRequest.class);
            if (request == null) {
                continue;
            }
            createPacketHandlerAdapter(declaredMethod,bean);
        }
        return bean;
    }

    private void createPacketHandlerAdapter(Method method, Object bean) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 2) {
            throw new RuntimeException("gameController方法参数数量须为2:" + bean.getClass().getName() + "--:" + method.getName());
        }
        Class<?> sessionClass = parameterTypes[0];
        if (sessionClass != GameSession.class) {
            throw new RuntimeException("gameController参数1需为player:" + bean.getClass().getName() + "--:" + method.getName());
        }
        Class<?> packetClass = parameterTypes[1];
        if (AbstractPacket.class.isAssignableFrom(packetClass)) {
            throw new RuntimeException("gameController参数2需为AbstractPacket:" + bean.getClass().getName() + "--:" + method.getName());
        }
        PacketHandlerAdapter adapter = new PacketHandlerAdapter(bean,method);
        PacketHandlerAdapter old = handlerAdapters.putIfAbsent(packetClass, adapter);
        if (old != null) {
            throw new RuntimeException("一个协议只能有一个处理方法:" + packetClass.getName());
        }
    }
}

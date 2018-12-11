package lucas.core.socket.net.message;

import lucas.common.service.IService;
import lucas.common.service.ServiceName;
import org.springframework.stereotype.Service;

/**
 * @author lushengkao vip8
 * 2018/11/6 10:51
 */
@Service
public class MessageRegistry implements IService {

    @Override
    public String getId() {
        return ServiceName.MESSAGE_REGISTRY;
    }

    @Override
    public void startUp() {

    }

    @Override
    public void shutDown() {

    }
}

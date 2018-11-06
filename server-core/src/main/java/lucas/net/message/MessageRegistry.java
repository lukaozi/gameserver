package lucas.net.message;

import lucas.core.service.IService;
import lucas.core.service.Reloable;
import lucas.core.service.ServiceName;

/**
 * @author lushengkao vip8
 * 2018/11/6 10:51
 */
public class MessageRegistry implements IService, Reloable {

    @Override
    public String getId() {
        return ServiceName.MESSAAGE_REGISTRY;
    }

    @Override
    public void startUp() {

    }

    @Override
    public void shutDown() {

    }

    @Override
    public void reload() {

    }
}

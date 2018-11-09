package lucas.core.socket.bootstarp.manager;

import lucas.core.socket.net.LocalNetService;

/**
 * @author lushengkao vip8
 * 本地实例管理
 * 2018/10/16 20:16
 */
public class LocalMagager {

    public static final LocalMagager INSTANCE = new LocalMagager();

    private LocalNetService localNetService;

    public LocalNetService getLocalNetService() {
        return localNetService;
    }

    public void setLocalNetService(LocalNetService localNetService) {
        this.localNetService = localNetService;
    }
}

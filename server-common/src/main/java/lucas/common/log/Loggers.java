package lucas.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Loggers {

    Logger NET_ERROR_LOGGER = LoggerFactory.getLogger("net-error");

    Logger SERVER_LOGGER = LoggerFactory.getLogger("server");
}

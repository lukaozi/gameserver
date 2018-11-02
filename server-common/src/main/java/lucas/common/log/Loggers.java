package lucas.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Loggers {

    Logger NET_ERROR_LOGGER = LoggerFactory.getLogger("net_error");

    Logger SERVER_LOGGER = LoggerFactory.getLogger("server");

    Logger REDIS = LoggerFactory.getLogger("redis");

    Logger EVENT_BUS = LoggerFactory.getLogger("event_bus");
}

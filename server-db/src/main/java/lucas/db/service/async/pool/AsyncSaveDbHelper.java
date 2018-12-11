package lucas.db.service.async.pool;

import lucas.common.util.ThreadPoolUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author lushengkao vip8
 * 2018/12/11 20:37
 */
@Component
public class AsyncSaveDbHelper{

    private static final int THREAD_COUNT = 1;

    private ScheduledThreadPoolExecutor[] executors;

    @PostConstruct
    private void init() {
        this.executors = ThreadPoolUtils.createSingleExecutorServiceArray("async-save", THREAD_COUNT);
    }


}

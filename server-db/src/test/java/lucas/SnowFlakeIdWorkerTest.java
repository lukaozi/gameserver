package lucas;


import lucas.common.GlobalContant;
import lucas.db.utils.idgenerator.SnowFlakeIdWorker;
import org.junit.Test;

/**
 * @author lushengkao vip8
 * 2018/11/27 14:42
 */
public class SnowFlakeIdWorkerTest {

    @Test
    public void t1() {
        SnowFlakeIdWorker idWorker = new SnowFlakeIdWorker(GlobalContant.SERVER_NO);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }
    }
}

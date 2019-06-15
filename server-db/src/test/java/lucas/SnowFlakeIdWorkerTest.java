package lucas;


import lucas.common.GlobalConstant;
import lucas.mysql.utils.idgenerator.SnowFlakeIdWorker;
import org.junit.Test;

/**
 * @author lushengkao vip8
 * 2018/11/27 14:42
 */
public class SnowFlakeIdWorkerTest {

    @Test
    public void t1() {
        SnowFlakeIdWorker idWorker = new SnowFlakeIdWorker(GlobalConstant.getServerNo());
        long last = 0;
        for (int i = 0; i < 10000; i++) {
            long id = idWorker.nextId();
            if (last > id){
                throw new RuntimeException("");
            }
            last = id;
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }
    }
}

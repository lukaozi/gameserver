package lucas.core.db;

import lucas.common.util.ApplicationContextUtils;
import lucas.core.game.player.entity.PlayerEntity;
import lucas.core.game.player.entity.PlayerMapper;
import lucas.core.game.player.service.PlayerEntityService;
import lucas.core.game.player.service.PlayerManager;
import lucas.db.redis.lock.RedisLock;
import lucas.db.utils.idgenerator.IDGenerator;
import lucas.db.utils.idgenerator.IDType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author lushengkao vip8
 * 2018/11/14 16:51
 */

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:server/applicationContext.xml"}) //加载配置文件
public class MybatisTest {

    @Test
    public void t1() {
        ApplicationContext applicationContext = ApplicationContextUtils.getApplicationContext();
        SqlSessionTemplate sqlSessionTemplate = applicationContext.getBean(SqlSessionTemplate.class);
        PlayerEntity playerEntity = new PlayerEntity("lushengkao",11L);
        playerEntity.setName("lushengkao33");
        PlayerMapper mapper = sqlSessionTemplate.getMapper(PlayerMapper.class);
        mapper.insertEntity(playerEntity);
    }

    @Test
    public void t2() {
        ApplicationContext applicationContext = ApplicationContextUtils.getApplicationContext();
        PlayerEntity playerEntity = new PlayerEntity("测试", IDGenerator.createId(IDType.PLAYER_ID));
        playerEntity.setName("考神");
        PlayerManager playerManager = applicationContext.getBean(PlayerManager.class);
        PlayerEntityService entityService = playerManager.getPlayerEntityService();
        entityService.insertEntity(playerEntity);
        PlayerEntity entity = entityService.getEntity(playerEntity.getId());
        entity.setName("猪猪");
        entityService.updateEntity(entity);
        PlayerEntity entity1 = entityService.getEntity(playerEntity.getId());
        System.out.println(entity1.getName());
    }

    private static volatile int count = 0;

    @Test
    public void t3() throws InterruptedException {
        long time1 = System.currentTimeMillis();
        ConcurrentHashMap<Integer,Boolean> map = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        for (int i = 0; i < 300; i++) {
            executorService.submit(() -> {
                RedisLock redisLock = new RedisLock("test");
                try {
                    for (;;) {
                        if (redisLock.tryLock()) {
                            break;
                        }
                    }
                    count = count + 1;
                    Boolean result = map.putIfAbsent(count, true);
                    if (result != null) {
                        System.out.println("并发异常 + ");
                    }
                    if (count == 300) {
                        long time2 = System.currentTimeMillis() - time1;
                        System.out.println("时间" + time2);

                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    redisLock.unLock();
                }
            });
        }
        Thread.sleep(5000);
        System.out.println(count);
    }
}

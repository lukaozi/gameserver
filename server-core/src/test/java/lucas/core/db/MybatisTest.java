package lucas.core.db;

import lucas.common.util.ApplicationContextUtils;
import lucas.core.game.player.entity.PlayerEntity;
import lucas.core.game.player.entity.PlayerMapper;
import lucas.core.game.player.service.PlayerEntityService;
import lucas.core.game.player.service.PlayerManager;
import lucas.db.entity.IEntity;
import lucas.db.redis.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

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
        PlayerEntity playerEntity = new PlayerEntity("kkaa",79339);
        playerEntity.setName("考神2");
        PlayerManager playerManager = applicationContext.getBean(PlayerManager.class);
        PlayerEntityService entityService = playerManager.getPlayerEntityService();
        entityService.insertEntity(playerEntity);
        PlayerEntity entity = entityService.getEntity(79339);
        System.out.println(entity.getName());
    }
}

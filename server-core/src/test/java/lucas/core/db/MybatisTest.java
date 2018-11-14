package lucas.core.db;

import lucas.common.util.ApplicationContextUtil;
import lucas.core.game.player.entity.PlayerEntity;
import lucas.core.game.player.entity.PlayerMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lushengkao vip8
 * 2018/11/14 16:51
 */

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:applicationContext.xml"}) //加载配置文件
public class MybatisTest {

    @Test
    public void t1() {
        ApplicationContext applicationContext = ApplicationContextUtil.getApplicationContext();
        SqlSessionTemplate sqlSessionTemplate = applicationContext.getBean(SqlSessionTemplate.class);
        PlayerEntity playerEntity = new PlayerEntity();
        playerEntity.setId(1L);
        playerEntity.setName("lushengkao");
        PlayerMapper mapper = sqlSessionTemplate.getMapper(PlayerMapper.class);
        mapper.insertEntity(playerEntity);
    }
}

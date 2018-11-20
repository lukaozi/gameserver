package lucas.core.game.player.service;

import lucas.core.game.player.entity.PlayerEntity;
import lucas.db.service.EntityService;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

/**
 * @author lushengkao vip8
 * 2018/11/20 22:11
 */
@Component
public class PlayerEntityService extends EntityService<PlayerEntity> {

    public PlayerEntityService(SqlSessionTemplate sqlSessionTemplate) {
        super(PlayerEntity.class, sqlSessionTemplate);
    }
}

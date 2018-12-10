package lucas.core.game.player.entity;

import lucas.core.game.player.Player;
import lucas.db.annnotation.CacheMethod;
import lucas.db.annnotation.DbMapper;
import lucas.db.annnotation.EntitySave;
import lucas.db.annnotation.CacheField;
import lucas.db.entity.BaseLongIdEntity;
import lucas.db.redis.RedisInterface;
import lucas.db.redis.contant.RedisKey;

/**
 * @author lushengkao vip8
 * 2018/11/13 17:36
 */

@EntitySave
@DbMapper(mapper = PlayerMapper.class)
public class PlayerEntity extends BaseLongIdEntity implements RedisInterface {

    public PlayerEntity() {
    }

    public PlayerEntity(String account, long id) {
        this.account = account;
        setId(id);
    }

    private transient Player player = new Player(this);

    @CacheField
    private String account;

    @CacheField
    private String name;

    @CacheField
    private int level;

    public String getName() {
        return name;
    }

    @CacheMethod("name")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.PLAYER;
    }

    public String getAccount() {
        return account;
    }

    @CacheMethod("account")
    public void setAccount(String account) {
        this.account = account;
    }

    @CacheMethod("level")
    public void setLevel(int level) {
        this.level = level;
    }

    public Player getPlayer() {
        return player;
    }

    public int getLevel() {
        return level;
    }
}

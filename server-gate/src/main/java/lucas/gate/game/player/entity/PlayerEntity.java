package lucas.gate.game.player.entity;

import lucas.common.util.FastJsonUtils;
import lucas.gate.game.bag.model.Bag;
import lucas.gate.game.player.Player;
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

    public PlayerEntity(String account, long id) {
        this.account = account;
        setId(id);
    }

    private Player player = new Player(this);

    @CacheField
    private String account;

    @CacheField
    private String name;

    @CacheField
    private int level;

    @CacheField
    private String bagJson;
    //背包
    private Bag bag;

    @Override
    public RedisKey getRedisKey() {
        return RedisKey.PLAYER;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void serialize() {
        setBagJson(FastJsonUtils.toJson(bag));
    }

    @Override
    public void deserialize() {
        if (bag == null) {
            if (bagJson == null) {
                bag = new Bag();
            }else {
                bag = FastJsonUtils.fromJson(bagJson, Bag.class);
            }
        }
    }

    public String getBagJson() {
        return bagJson;
    }

    @CacheMethod("bagJson")
    public void setBagJson(String bagJson) {
        this.bagJson = bagJson;
    }

    public String getName() {
        return name;
    }

    @CacheMethod("name")
    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    @CacheMethod("level")
    public void setLevel(int level) {
        this.level = level;
    }

    public String getAccount() {
        return account;
    }

    @CacheMethod("account")
    public void setAccount(String account) {
        this.account = account;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }
}

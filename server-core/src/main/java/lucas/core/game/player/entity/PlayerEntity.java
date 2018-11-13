package lucas.core.game.player.entity;

import lucas.db.annnotation.EntitySave;
import lucas.db.annnotation.FieldSave;
import lucas.db.entity.BaseLongIdEntity;

/**
 * @author lushengkao vip8
 * 2018/11/13 17:36
 */

@EntitySave
public class PlayerEntity extends BaseLongIdEntity {

    @FieldSave
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

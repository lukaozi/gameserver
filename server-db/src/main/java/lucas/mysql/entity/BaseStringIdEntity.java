package lucas.mysql.entity;

import lucas.mysql.annnotation.CacheField;

/**
 * @author lushengkao vip8
 * 以String为主键的实体类型
 * 2018/10/22 15:25
 */
public abstract class BaseStringIdEntity extends AbstractEntity<String> {

    @CacheField
    private String userId;

    public String getId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = id;
    }
}

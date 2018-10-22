package lucas.db.entity;

/**
 * @author lushengkao vip8
 * 以String为主键的实体类型
 * 2018/10/22 15:25
 */
public class BaseStringIdEntity extends AbstractEntity<String> {

    private String userId;

    public String getId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = id;
    }
}

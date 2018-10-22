package lucas.db.entity;

/**
 * @author lushengkao vip8
 * 以long为主键的实体类型
 * 2018/10/22 15:14
 */
public class BaseLongIdEntity implements IEntity<Long>{

    private Long userId;

    public Long getId() {
        return userId;
    }

    public void setId(Long id) {
        this.userId = id;
    }
}

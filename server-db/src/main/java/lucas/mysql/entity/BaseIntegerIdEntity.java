package lucas.mysql.entity;

/**
 * @author lushengkao vip8
 * 以Integer为主键的实体类型
 * 2018/10/22 15:24
 */
public abstract class BaseIntegerIdEntity extends AbstractEntity<Integer> {

    private Integer userId;


    public Integer getId() {
        return userId;
    }

    public void setId(Integer id) {
        this.userId = id;
    }
}

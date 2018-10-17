package lucas.db.annnotation;

import java.lang.annotation.*;

/**
 * create by lushengkao 2018/10/14 23:46
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DbMapper {
    /**
     * @return
     */
    Class mapper();
}

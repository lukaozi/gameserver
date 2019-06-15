package lucas.mysql.annnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lushengkao vip8
 * 方法加此注解收集更新的数值
 * 2018/11/19 11:33
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheMethod {
    String value();
}

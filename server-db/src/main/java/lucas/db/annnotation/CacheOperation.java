package lucas.db.annnotation;

import lucas.db.enums.OperationEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create by lushengkao 2018/10/14 23:46
 * 对应的curd操作
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheOperation {

    OperationEnum value() default OperationEnum.none;
}


package lucas.mysql.annnotation;

import lucas.mysql.mapper.IDBMapper;

import java.lang.annotation.*;

/**
 * create by lushengkao 2018/10/14 23:46
 * 和mybatis相映射
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DbMapper {

    Class<? extends IDBMapper> mapper();
}

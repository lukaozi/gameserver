package lucas.gamecode.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author lushengkao vip8
 * 2018/11/8 20:27
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface GameController {
}

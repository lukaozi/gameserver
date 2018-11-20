package lucas.common.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author lushengkao vip8
 * 2018/11/19 17:47
 */
public class BeanUtils {

    public static Object getFieldValue(Object obj, String fieldName) {
        Field field = ReflectionUtils.findField(obj.getClass(), fieldName);
        if (field == null) {
            return null;
        }
        field.setAccessible(true);
        return ReflectionUtils.getField(field, obj);
    }

    public static boolean equalsValue(Object oldValue, Object newValue) {
        if (newValue == null) {
            return oldValue == null;
        }
        return newValue.equals(oldValue);
    }
}

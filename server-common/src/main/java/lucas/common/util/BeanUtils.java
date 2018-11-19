package lucas.common.util;

import java.lang.reflect.Field;

/**
 * @author lushengkao vip8
 * 2018/11/19 17:47
 */
public class BeanUtils {

    public static Object getFieldValue(Object obj, String fieldName) {
        Field field = getDeclaredField(obj, fieldName);
        field.setAccessible(true);
        try {
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Field getDeclaredField(Object obj, String fieldName) {
        Class<?> clazz = obj.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    public static boolean equalsValue(Object oldValue, Object newValue) {
        if (newValue == null) {
            return oldValue == null;
        }
        return newValue.equals(oldValue);
    }
}

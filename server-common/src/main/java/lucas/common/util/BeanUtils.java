package lucas.common.util;

import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

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

    public static Field getFiledByName(Object object, String fileName){
        Class<?> clazz = object.getClass();
        Class<?> TmpClazz = clazz;
        do {
            try {
                return TmpClazz.getDeclaredField(fileName);
            } catch (NoSuchFieldException ignored) {}
        }while ((TmpClazz = clazz.getSuperclass()) != Object.class);
        return null;
    }

    public static List<Field> getFieldsWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Field> result = new LinkedList<>();
        Class<?> TmpClazz = clazz;
        do {
            Field[] fields = TmpClazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(annotation)  != null) {
                    result.add(field);
                }
            }
        }while ((TmpClazz = clazz.getSuperclass()) == Object.class);
        return result;
    }
}

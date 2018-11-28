package lucas.common.util;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author lushengkao
 * 对应数据库中的字段
 * 2018/11/28 11:22
 */
public class SQLFileStringUtils {

    public static String toString(Object object) {
        if (object == null) {
            return "";
        }
        if (object instanceof Date) {
            return TimeUtils.dateToString((Date) object);
        }
        return object.toString();
    }

    public static Object fromString(String text, Class<?> clazz) {
        if (clazz == Integer.class || clazz == int.class) {
            return Integer.parseInt(text);
        } else if (clazz == Boolean.class || clazz == boolean.class) {
            return Boolean.parseBoolean(text);
        } else if (clazz == Long.class || clazz == long.class) {
            return Long.parseLong(text);
        } else if (clazz == Float.class || clazz == float.class) {
            return Float.parseFloat(text);
        } else if (clazz == Double.class || clazz == double.class) {
            return Double.parseDouble(text);
        } else if (clazz == Byte.class || clazz == byte.class) {
            return Byte.parseByte(text);
        } else if (clazz == Short.class || clazz == short.class) {
            return Short.parseShort(text);
        } else if (clazz == String.class) {
            return text;
        } else if (clazz == Date.class) {
            return TimeUtils.stringToDate(text);
        } else if (clazz == Timestamp.class) {
            return TimeUtils.stringToTimestamp(text);
        }
        return null;
    }
}

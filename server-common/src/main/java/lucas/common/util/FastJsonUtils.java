package lucas.common.util;

import com.alibaba.fastjson.JSON;

/**
 * @author lushengkao vip8
 * 2018/11/28 10:36
 */
public class FastJsonUtils {

    public static String toJson(Object object) {
        return JSON.toJSONString(object);
    }

    public static <T> T fromJson(String json,Class<T> clazz) {
        return JSON.parseObject(json,clazz);
    }
}

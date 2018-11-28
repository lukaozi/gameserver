package lucas.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lushengkao vip8
 * 2018/11/28 11:33
 */
public class TimeUtils {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return simpleDateFormat.format(date);
    }


    /**
     * 字符串转日期
     */
    public static Date stringToDate(String stringValue){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        try {
            return simpleDateFormat.parse(stringValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转日期 按默认格式
     */
    public static Timestamp stringToTimestamp(String stringValue){
        Date date =  stringToDate(stringValue);
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }
}

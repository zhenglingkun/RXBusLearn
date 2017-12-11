package zlk.com.rxbuslearn.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/*********************************************************************************
 * 日期处理类
 *********************************************************************************/
public class DateTime {


    /*********************************************************************************
     * 获取年月日
     *********************************************************************************/
    public static String getDate() {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = formatDate.format(new Date(System.currentTimeMillis()));
        return date;
    }


    /*********************************************************************************
     * 获取年月日时分秒
     *********************************************************************************/
    public static String getDateTime() {
        SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String dateTime = formatDateTime.format(new Date(System.currentTimeMillis()));
        return dateTime;
    }
}

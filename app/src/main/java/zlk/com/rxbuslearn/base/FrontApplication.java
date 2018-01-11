package zlk.com.rxbuslearn.base;

/**
 * Created by zlk on 2018/1/11 0011.
 * this is a 工具类 for 判断当前应用是否在前台运行
 */

public class FrontApplication {

    /**
     * Activity计数
     */
    private static int activityVisibleCount;

    /**
     * 判断当前App是否在前台运行的方法？ 只要检查计数是否>0
     *
     * @return true:在前台运行
     */
    public static boolean isAppRunningFront() {
        return activityVisibleCount > 0;
    }

    /**
     * 当任意activity进入onResue()时，增加计数；当退出前台时进入onPause()时，减少计数
     *
     * @param isAppRunningFront true:计数加1；false:计数减1
     */
    public static void setIsAppRunningFront(boolean isAppRunningFront) {
        if (isAppRunningFront) {
            activityVisibleCount++;
        } else if (activityVisibleCount > 0) {
            activityVisibleCount--;
        }
    }

}

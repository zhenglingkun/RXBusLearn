package zlk.com.rxbuslearn.base;

import android.app.Application;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.PrintStream;

import zlk.com.rxbuslearn.log.LogConfigManager;
import zlk.com.rxbuslearn.util.FileUtils;
import zlk.com.rxbuslearn.util.LogUtil;

/**
 * @author zlk
 * @time 2017/12/11.
 */

public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        LogConfigManager.getInstance(this, "KClientLog").OpenLog();

        configUncaughtExceptionHandler();
    }

    /**
     * 捕获异常
     */
    private void configUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                LogUtil.e(TAG, "uncaughtException crash");
                Toast.makeText(BaseApplication.this, "crash", Toast.LENGTH_LONG).show();
                try {
                    ex.printStackTrace(new PrintStream(FileUtils.createErrorFile()));
                } catch (FileNotFoundException e) {
                    LogUtil.e(TAG, "创建异常文件失败");
                    e.printStackTrace();
                }

            }

        });
    }

}

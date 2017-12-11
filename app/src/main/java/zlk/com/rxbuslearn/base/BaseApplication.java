package zlk.com.rxbuslearn.base;

import android.app.Application;

import zlk.com.rxbuslearn.log.LogConfigManager;

/**
 * @author zlk
 * @time 2017/12/11.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogConfigManager.getInstance(this, "KClientLog").OpenLog();
    }
}

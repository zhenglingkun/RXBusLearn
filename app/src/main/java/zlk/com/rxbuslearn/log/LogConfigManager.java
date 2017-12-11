package zlk.com.rxbuslearn.log;

import android.content.Context;

public class LogConfigManager {
    private static LogConfigManager instance;
    private LogApi mLogApi = null;
    private Context context;

    public synchronized static LogConfigManager getInstance(Context context, String logDir) {
        if (instance == null) {
            instance = new LogConfigManager(context, logDir);
        }
        return instance;
    }

    public synchronized static LogConfigManager getInstance(Context context) {
        if (instance == null) {
            instance = new LogConfigManager(context);
        }
        return instance;
    }

    private LogConfigManager(Context context) {
        this.context = context;
        mLogApi = LogApi.getInstance();
    }

    private LogConfigManager(Context context, String logDir) {
        this.context = context;
        mLogApi = LogApi.getInstance(logDir);
    }


    public void OpenLog() {
        mLogApi.turnCrashLogOn(context);
        mLogApi.IsShowPhoneInfo(false);
        mLogApi.SetCrashDialog("未知错误");

        mLogApi.turnDebugLogOn();
        mLogApi.setDebugLogLevel(LogLevel.INFO);
    }

    public void CloseLog() {
        if (mLogApi != null) {
            mLogApi.turnDebugLogOff();
        }

    }
}

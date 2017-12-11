package zlk.com.rxbuslearn.log;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/*********************************************************************************
 * Uncaught异常处理类：程序发生Uncaught异常时,由该类来接管程序,并记录发送错误报告
 *********************************************************************************/
public class LogCrashHandler implements UncaughtExceptionHandler {
    // 本类实例
    private static LogCrashHandler INSTANCE = null;
    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> mPhoneInfo = new HashMap<String, String>();
    // 是否显示设备参数信息
    private boolean mIsShowPhoneInfo = true;
    // Crash信息
    private String mCrashInformation;
    // 日志保存路径
    private String mLogSavePath;
    // 应用程序名
    private String mAppName;


    /*********************************************************************************
     * 获取本类实例
     *********************************************************************************/
    public synchronized static LogCrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LogCrashHandler();
        }
        return INSTANCE;
    }

    /*********************************************************************************
     * 私有构造函数
     *********************************************************************************/
    private LogCrashHandler() {

    }


    /*********************************************************************************
     * 初始化
     *********************************************************************************/
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    /*********************************************************************************
     * 当UncaughtException发生时会转入该重写的方法来处理
     *********************************************************************************/
    public void uncaughtException(Thread thread, Throwable exception) {
        if (!handleException(exception) && mDefaultHandler != null) {
            // 如果自定义的没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, exception);
        } else {
            try {
                Thread.sleep(3000);// 如果处理了，让程序继续运行3秒再退出，保证文件保存并上传到服务器
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 退出程序
            //android.os.Process.killProcess(android.os.Process.myPid());
            //System.exit(1);
            ((Activity) mContext).finish();
        }
    }

    /*********************************************************************************
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成
     * 参数exception：异常信息
     * 如果处理了该异常信息返回true; 否则返回false
     *********************************************************************************/
    public boolean handleException(Throwable exception) {
        if (exception == null)
            return false;
        new Thread() {
            public void run() {
                Looper.prepare();
                if (mCrashInformation != null && mCrashInformation.length() > 0) {
                    //	Toast.makeText(mContext, "抱歉,程序异常,即将退出", 0).show();
                    Toast.makeText(mContext, mCrashInformation, Toast.LENGTH_LONG).show();
                }
                Looper.loop();
            }
        }.start();
        // 收集设备参数信息
        collectDeviceInfo(mContext);
        // 保存日志文件
        saveCrashInfo(exception);
        return true;
    }


    /*********************************************************************************
     * 收集设备参数信息
     *********************************************************************************/
    public void collectDeviceInfo(Context context) {
        try {
            // 获得包管理器
            PackageManager pm = context.getPackageManager();
            // 得到该应用的信息，即主Activity
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                String packageName = pi.packageName + "";
                mPhoneInfo.put("versionName", versionName);
                mPhoneInfo.put("versionCode", versionCode);
                mPhoneInfo.put("packageName", packageName);
                // 获取程序名
                ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 0);
                mAppName = appInfo.loadLabel(pm).toString();
                mPhoneInfo.put("appName", mAppName);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        // 反射机制
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mPhoneInfo.put(field.getName(), field.get("").toString());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /*********************************************************************************
     * 保存日志文件
     *********************************************************************************/
    private void saveCrashInfo(Throwable exception) {
        StringBuffer strBuf = new StringBuffer();
        String strTime = DateTime.getDateTime();
        strBuf.append("---------------------------Crash Log started---------------------------\n" + strTime);

        // 手机信息
        for (Map.Entry<String, String> entry : mPhoneInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (mIsShowPhoneInfo) {
                strBuf.append(key + "=" + value + "\r\n");
            }
        }


        // Crash信息
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        exception.printStackTrace(pw);
        Throwable cause = exception.getCause();
        // 循环着把所有的异常信息写入writer中
        while (cause != null) {
            pw.write("\r\n");
            cause.printStackTrace(pw);
            cause = cause.getCause();

        }
        // 关闭 PrintWriter
        pw.close();
        String result = writer.toString();
        strBuf.append("\r\n-------------------------------Crash Bug Info-----------------------------\r\n");
        strBuf.append(result);
        strBuf.append("\r\n-------------------------------Crash Log end------------------------------\r\n");


        // 保存文件
        String fileName = "crash-" + DateTime.getDate() + ".log";
        FileTools.WriteByString(mLogSavePath,
                fileName, strBuf.toString(), true);
    }


    /*********************************************************************************
     * 设置发生Crash时的提示字符串
     *********************************************************************************/
    public void setCrashDialog(String info) {
        mCrashInformation = info;
    }


    /*********************************************************************************
     * 设置日志保存路径
     *********************************************************************************/
    public void setCrashLogSavePath(String path) {
        mLogSavePath = path;
    }


    /*********************************************************************************
     * 是否显示设备参数信息
     *********************************************************************************/
    public void setIsShowPhoneInfo(boolean aValue) {
        mIsShowPhoneInfo = aValue;
    }

}  

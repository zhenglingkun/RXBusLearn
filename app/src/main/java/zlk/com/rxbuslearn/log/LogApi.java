package zlk.com.rxbuslearn.log;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/*********************************************************************************
 * LogApi类：是LogHandler类的API,由该类 调用/设置LogHandler
 *********************************************************************************/

public class LogApi {
    private static LogApi instance = null;

    private String mLogDir = null;
    private String mCrashLogDir = null;
    private String mDebugLogDir = null;

    private StringBuilder mCrashLogPath = new StringBuilder();
    private StringBuilder mDebugLogPath = new StringBuilder();

    private LogCrashHandler mCrashLogHandler = null;
    private LogDebugHandler mDebugLogHandler = null;
    private int mDebugLogLevel = 0;

    /*********************************************************************************
     * 获取LogApi实例：创建默认目录
     * 单例模式保证只有一个LogApi实例
     *********************************************************************************/
    public synchronized static LogApi getInstance() {
        return getInstance("");
    }

    /*********************************************************************************
     * 获取LogApi实例：创建默认目录
     * 单例模式保证只有一个LogApi实例
     *********************************************************************************/
    public synchronized static LogApi getInstance(String logDir) {
        return getInstance(logDir + "AppLog", "crash", "debug");
    }

    /*********************************************************************************
     * 获取LogApi实例：用户指定目录
     * 单例模式保证只有一个LogApi实例
     *********************************************************************************/
    public synchronized static LogApi getInstance(String logDir, String crashDir, String debugDir) {
        if (instance == null) {
            instance = new LogApi(logDir, crashDir, debugDir);
        }
        return instance;
    }

    private LogApi(String logDir, String crashDir, String debugDir) {

        mLogDir = logDir;
        mCrashLogDir = crashDir;
        mDebugLogDir = debugDir;

        mCrashLogHandler = LogCrashHandler.getInstance();
        mDebugLogHandler = LogDebugHandler.getInstance();
    }

    /*********************************************************************************
     * 设置日志保存目录
     *********************************************************************************/
    public void SetLogSavePath() {
        mCrashLogPath.setLength(0);
        mDebugLogPath.setLength(0);

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            mCrashLogPath.append(Environment.getExternalStorageDirectory()
                    .getAbsolutePath()).append(File.separator).append(mLogDir)
                    .append(File.separator).append(mCrashLogDir);

            mDebugLogPath.append(Environment.getExternalStorageDirectory()
                    .getAbsolutePath()).append(File.separator).append(mLogDir)
                    .append(File.separator).append(mDebugLogDir);

        } else {
            mCrashLogPath.append(Environment.getDataDirectory()
                    .getAbsolutePath()).append(File.separator).append(mLogDir)
                    .append(File.separator).append(mCrashLogDir);

            mDebugLogPath.append(Environment.getDataDirectory()
                    .getAbsolutePath()).append(File.separator).append(mLogDir)
                    .append(File.separator).append(mDebugLogDir);
        }


        File dir_crash = new File(mCrashLogPath.toString());
        if (!dir_crash.exists()) {
            dir_crash.mkdirs();
        }

        File dir_debug = new File(mDebugLogPath.toString());
        if (!dir_debug.exists()) {
            dir_debug.mkdirs();
        }
    }

    /*********************************************************************************
     * 开启日志记录  及相关参数设置
     *********************************************************************************/
    public void turnCrashLogOn(Context context) {
        SetLogSavePath();

        mCrashLogHandler = LogCrashHandler.getInstance();
        mCrashLogHandler.setCrashLogSavePath(mCrashLogPath.toString());
        mCrashLogHandler.init(context);
    }

    /*********************************************************************************
     * 设置异常发生时的提示文本
     *********************************************************************************/
    public void SetCrashDialog(String crashInfo) {
        mCrashLogHandler = LogCrashHandler.getInstance();
        mCrashLogHandler.setCrashDialog(crashInfo);
    }

    /*********************************************************************************
     * 设置是否显示设备信息
     *********************************************************************************/
    public void IsShowPhoneInfo(boolean aValue) {
        mCrashLogHandler = LogCrashHandler.getInstance();
        mCrashLogHandler.setIsShowPhoneInfo(aValue);
    }

    /*********************************************************************************
     * 打开一般日志记录
     *********************************************************************************/
    public void turnDebugLogOn() {
        SetLogSavePath();

        mDebugLogHandler.setDebugLogSavePath(mDebugLogPath.toString());
        mDebugLogHandler.startDetailLog();
    }

    /*********************************************************************************
     * 关闭一般日志记录
     *********************************************************************************/
    public void turnDebugLogOff() {
        mDebugLogHandler.stopDetailLog();
    }

    // 设置写入Log的级别, 如写入Log.i, 或Log.e等

    /*********************************************************************************
     * 设置日志级别
     * 不设置则显示全部Log
     *********************************************************************************/
    public void setDebugLogLevel(int value) {
        mDebugLogLevel = value;
    }

    /*********************************************************************************
     * 在这里做判断是否写入文件
     *********************************************************************************/
    public void debug(String tag, String msg) {
        if (!(mDebugLogLevel > LogLevel.DEBUG)) {
            mDebugLogHandler.logToFile(LogLevel.DEBUG, tag, msg);
        }
        Log.d(tag, msg);
    }

    public void info(String tag, String msg) {
        if (!(mDebugLogLevel > LogLevel.INFO)) {
            mDebugLogHandler.logToFile(LogLevel.INFO, tag, msg);
        }
        Log.i(tag, msg);
    }

    public void warn(String tag, String msg) {
        if (!(mDebugLogLevel > LogLevel.WARN)) {
            mDebugLogHandler.logToFile(LogLevel.WARN, tag, msg);
        }
        Log.w(tag, msg);
    }

    public void error(String tag, String msg) {
        if (!(mDebugLogLevel > LogLevel.ERROR)) {
            mDebugLogHandler.logToFile(LogLevel.ERROR, tag, msg);
        }
        Log.e(tag, msg);
    }


    /*********************************************************************************
     * 发送Crash日志: 以 byte数组 形式上传
     *********************************************************************************/
    public void sendCrashLog(String url) {
        File dir = new File(mCrashLogPath.toString());
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().indexOf("crash") > -1) {
                FileTools.SenByteViaNet(FileTools.ReadByByte(files[i]), url);
            }
        }
    }


    /*********************************************************************************
     * 发送Detail日志: 压缩 后以 通过网络以 附件 的形式发送
     *********************************************************************************/
    public void sendDetailLog(String url) {
        File dir = new File(mDebugLogPath.toString());

        // 先压缩
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().indexOf("detail") > -1
                    && files[i].getName().indexOf(".gz") < 0) {
                FileTools.doCompressFile(mDebugLogPath.toString(),
                        files[i].getName());
            }
        }

        // 然后发送
        Map<String, File> filesMap = new HashMap<String, File>();
        File[] filesC = dir.listFiles();
        for (int i = 0; i < filesC.length; i++) {
            if (filesC[i].getName().indexOf("detail") > -1
                    && filesC[i].getName().indexOf(".gz") > 0) {
                filesMap.put("File" + Integer.toString(i), new File(
                        mDebugLogPath.toString(), filesC[i].toString()));
            }
        }
    }
}

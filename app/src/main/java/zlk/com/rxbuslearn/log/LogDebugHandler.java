package zlk.com.rxbuslearn.log;


/*********************************************************************************
 * 一般的调试日志处理类：将应用的大量log信息保存到本地
 *********************************************************************************/
public class LogDebugHandler {
    // 本类实例
    private static LogDebugHandler INSTANCE = null;

    private String mLogSavePath = null;
    private String mLogFileName = null;
    private boolean mIsLog = false;

    /*********************************************************************************
     * 获取本类实例
     *********************************************************************************/
    public synchronized static LogDebugHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LogDebugHandler();
        }
        return INSTANCE;
    }

    private LogDebugHandler() {

    }

    /*********************************************************************************
     * 打开日志记录功能
     *********************************************************************************/
    public void startDetailLog() {
        mIsLog = true;
    }

    /*********************************************************************************
     * 关闭日志记录功能
     *********************************************************************************/
    public void stopDetailLog() {
        mIsLog = false;
    }

    /*********************************************************************************
     * 设置日志保存路径
     *********************************************************************************/
    public void setDebugLogSavePath(String path) {
        mLogSavePath = path;
    }

    /*********************************************************************************
     * 设置日志文件名
     *********************************************************************************/
    private void setDetailLogFileName() {
        mLogFileName = "detail-" + DateTime.getDate() + ".log";
    }


    /*********************************************************************************
     * 写日志到文件中
     *********************************************************************************/
    public void logToFile(int logType, String tag, String msg) {
        if (!mIsLog) {
            return;
        }

        StringBuilder strBld = new StringBuilder("");
        switch (logType) {
            case LogLevel.DEBUG:
                strBld.append("--Debug: ");
                strBld.append(DateTime.getDateTime()).append(" ").append(tag)
                        .append(" ").append(msg).append("\n");
                break;
            case LogLevel.INFO:
                strBld.append("--Info: ");
                strBld.append(DateTime.getDateTime()).append(" ").append(tag)
                        .append(" ").append(msg).append("\n");
                break;
            case LogLevel.WARN:
                strBld.append("--Warn: ");
                strBld.append(DateTime.getDateTime()).append(" ").append(tag)
                        .append(" ").append(msg).append("\n");
                break;
            case LogLevel.ERROR:
                strBld.append("--Error: ");
                strBld.append(DateTime.getDateTime()).append(" ").append(tag)
                        .append(" ").append(msg).append("\n");
                break;
            default:
                break;
        }
        setDetailLogFileName();
        if (strBld.length() > 0) {
            FileTools.WriteByString(mLogSavePath, mLogFileName,
                    strBld.toString(), true);
        }
    }
}

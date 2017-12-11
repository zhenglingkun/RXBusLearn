package zlk.com.rxbuslearn.log;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.zip.GZIPOutputStream;

/*********************************************************************************
 * 文件处理类
 *********************************************************************************/
public class FileTools {


    /*********************************************************************************
     * 保存字符串到文件
     *********************************************************************************/
    public static synchronized String WriteByString(String folder, String fileName,
                                                    String info, boolean isAppend) {
        try {
            File dir;
            dir = new File(folder);
            Log.i("WriteByString", dir.toString());
            Log.i("WriteByString", info);
            if (!dir.exists()) {
                dir.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(
                    new File(dir, fileName), isAppend);

            fos.write(info.getBytes());
            fos.close();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*********************************************************************************
     * 保存字节到文件
     *********************************************************************************/
    public static String WriteByByte(String folder, String fileName,
                                     byte[] bytes, boolean isAppend) {
        try {
            File dir;
            dir = new File(folder);
            Log.i("WriteByByte", dir.toString());
            if (!dir.exists()) {
                dir.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(
                    new File(dir, fileName), isAppend);

            // 获取FileChannel对象
            FileChannel fileChannel = fos.getChannel();
            // 获取文件锁, 同步机制, 用于处理并发的文件读写
            FileLock fileLock = fileChannel.tryLock();
            if (null != fileLock) {
                fos.write(bytes);
            }
            //释放文件锁
            fileLock.release();
            // 释放FileChannel对象
            fileChannel.close();

            fos.close();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /*********************************************************************************
     * 按字节读文件
     *********************************************************************************/
    public static byte[] ReadByByte(File fileName) {
        byte[] byteData = null;
        // 如果从FileInputStream中直接获得FileChannel的对象,
        // 然后直接操作FileLock会报异常NonWritableChannelException ;
        // 由于两个对文件进行写的操作都已经加锁, 所以读不必加锁
        FileInputStream fileInStream = null;

        try {
            fileInStream = new FileInputStream(fileName);
            int length = fileInStream.available();
            byteData = new byte[length];
            fileInStream.read(byteData);
            fileInStream.close();
            fileInStream = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return byteData;
    }


    /*********************************************************************************
     * 压缩文件, 压缩成功后得到名为 原文件名.gz 的新文件
     *********************************************************************************/
    public static void doCompressFile(String folder, String inFileName) {
        try {
            System.out.println("Creating the GZIP output stream.");
            String outFileName = inFileName + ".gz";
            FileOutputStream fOutStream = new FileOutputStream(new File(folder, outFileName));
            GZIPOutputStream gOutS = new GZIPOutputStream(fOutStream);

            System.out.println("Opening the input file.");
            FileInputStream fInS = new FileInputStream(new File(folder, inFileName));
            System.out.println("Transfering bytes from input file to GZIP Format.");
            byte[] buf = new byte[1024];
            int len;
            while ((len = fInS.read(buf)) > 0) {
                gOutS.write(buf, 0, len);
            }
            fInS.close();

            System.out.println("Completing the GZIP file");
            gOutS.finish();
            gOutS.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in Compressing GZIP file");
        }

    }


    /*********************************************************************************
     * 通过网络按字节发送文件
     *********************************************************************************/
    public static void SenByteViaNet(byte[] bData, String url) { /*
        HttpHandler httpHandler = new HttpHandler(url, HttpHandler.POST);
		httpHandler.setPostData(bData);
		httpHandler.setHttpHandlerListener(new HttpHandlerListener() {
			@Override
			public void onResponse(int responseCode, String responseStatusLine,
					String result) {
				if (responseCode == 200) {
					//发送成功
				} else {
					// 发送失败
				}
			}
		});
		httpHandler.execute();
		*/
    }

    /*********************************************************************************
     * 返回存储卡路径
     *********************************************************************************/
    public static String getExternalStoragePath() {
        String state = android.os.Environment.getExternalStorageState();
        if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
            File mDir = new File(android.os.Environment
                    .getExternalStorageDirectory().getPath()
                    + File.separator
                    + "PoleCollector");
            if (!mDir.exists()) {
                mDir.mkdirs();
            }
            return android.os.Environment.getExternalStorageDirectory()
                    .getPath();
        }
        return null;
    }


    /*********************************************************************************
     * 输入流到字符串
     *********************************************************************************/
    public static String inputStreamToString(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }


    /*********************************************************************************
     * 输入流到文件
     *********************************************************************************/
    public static void inputstreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

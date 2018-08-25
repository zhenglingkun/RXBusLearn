package zlk.com.rxbuslearn.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import zlk.com.rxbuslearn.R;
import zlk.com.rxbuslearn.base.dialog.TakePhotoDialog;
import zlk.com.rxbuslearn.util.BitmapUtils;
import zlk.com.rxbuslearn.util.CameraUtil;

/**
 * Created by ice on 2017/11/23 0023.
 * this is a xxx for 不对图片进剪切处理
 */

public abstract class BaseSelectImgActivity extends AppCompatActivity {

    public static final int REQUEST_CAMERA = 10118;
    public static final int REQUEST_PHOTO = 10119;

    @SuppressLint("SdCardPath")
    private static final String ACCOUNT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Image" + File.separator;
    public static final String ACCOUNT_MAINTRANCE_ICON_CACHE = "Template" + File.separator;
    private static final String IMG_PATH = ACCOUNT_DIR + ACCOUNT_MAINTRANCE_ICON_CACHE;
    protected String mRemarkImgPath;
    protected int mShowImgWidth;
    protected int mShowImgHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initShowImgWH();

        File directory = new File(ACCOUNT_DIR);
        File imagePath = new File(IMG_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (!imagePath.exists()) {
            imagePath.mkdir();
        }
    }

    protected abstract void initShowImgWH();

    public void showPicDialog() {
        // 弹出照片选择Dialog
        TakePhotoDialog dialog = new TakePhotoDialog();
        dialog.setOnSelectClickListener(new TakePhotoDialog.OnSelectClickListener() {
            @Override
            public void onCameraClick() {
                takeCamera();
            }

            @Override
            public void onPhotoClick() {
                getImageFromGallery();
            }
        });
        dialog.show(getSupportFragmentManager(), "CargoRemark");
    }

    private void takeCamera() {
        if (!CameraUtil.cameraIsCanUse()) {
            Toast.makeText(this, R.string.msg_camera_framework_bug, Toast.LENGTH_SHORT).show();
            return;
        }

        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Log.i("TestFile", "SD card is not avaiable/writeable right now.");
            Toast.makeText(this, "请插入SD卡", Toast.LENGTH_SHORT).show();
            return;
        }

        mRemarkImgPath = IMG_PATH + "remark.png";
        File remarkFile = new File(mRemarkImgPath);
        if (!remarkFile.exists()) {
            try {
                remarkFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mRemarkImgPath)));
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    public void getImageFromGallery() {
        try {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库
            startActivityForResult(i, REQUEST_PHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Toast.makeText(this, "请插入SD卡", Toast.LENGTH_SHORT).show();
                return;
            }
            new RemarkCameraTask().execute(mRemarkImgPath);
        } else if (requestCode == REQUEST_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
            new RemarkPhotoTask().execute(data.getData());
        }
    }

    private class RemarkCameraTask extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            FileInputStream fis = null;
            Bitmap bitmap = null;
            try {
                //把图片转化为字节流
                fis = new FileInputStream(params[0]);
                //把流转化图片
                bitmap = BitmapFactory.decodeStream(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();//关闭流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return BitmapUtils.getBitmapThumbnail(bitmap, mShowImgWidth, mShowImgHeight);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                showImg(bitmap);
            }
        }
    }

    protected abstract void showImg(Bitmap bitmap);

    public void delImg() {
        mRemarkImgPath = "";
    }

    /**
     * 保存文件，文件名为当前日期
     */
    protected void saveBitmap(Bitmap bitmap, String bitName) {
        String fileName;
        File file;
        if (Build.BRAND.equals("Xiaomi")) { // 小米手机
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + bitName;
        } else {  // Meizu 、Oppo
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/" + bitName;
        }
        file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
                // 插入图库
                MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), bitName, null);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 发送广播，通知刷新图库的显示
        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
    }

    private class RemarkPhotoTask extends AsyncTask<Uri, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(Uri... params) {
            mRemarkImgPath = getPath(getApplicationContext(), params[0]);
            Bitmap bitmap = BitmapFactory.decodeFile(mRemarkImgPath);
            return BitmapUtils.getBitmapThumbnail(bitmap, mShowImgWidth, mShowImgHeight);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                showImg(bitmap);
            }
        }
    }

    /**
     * 4.4及以上获取图片的方法
     *
     * @param context
     * @param uri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) { // MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) { // MediaStore (and general)

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) { // File
            return uri.getPath();
        }

        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

}

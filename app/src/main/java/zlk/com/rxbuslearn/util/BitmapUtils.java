package zlk.com.rxbuslearn.util;

import android.graphics.Bitmap;

/**
 * Created by ice on 2017/8/21 0021.
 * this is a xxx for
 */

public class BitmapUtils {

    /**
     * 图片压缩后按指定大小剪切
     *
     * @param bmp
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmapThumbnail(Bitmap bmp, int width, int height) {
        Bitmap bitmap = null;
        if (bmp != null) {
            int bmpWidth = bmp.getWidth();
            int bmpHeight = bmp.getHeight();

            int longerW = width * Math.max(bmpWidth, bmpHeight) / Math.min(bmpWidth, bmpHeight);
            int scaledWidth = bmpWidth > bmpHeight ? longerW : width;
            int scaledHeight = bmpWidth > bmpHeight ? height : longerW;
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bmp, scaledWidth, scaledHeight, true);

            int xTopLeft = (scaledWidth - width) / 2;
            int yTopLeft = (scaledHeight - height) / 2;

            bitmap = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, width, height);
            scaledBitmap.recycle();
        }
        return bitmap;
    }

}

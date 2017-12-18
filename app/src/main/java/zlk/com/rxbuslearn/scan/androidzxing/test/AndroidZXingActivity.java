package zlk.com.rxbuslearn.scan.androidzxing.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;

import zlk.com.rxbuslearn.R;
import zlk.com.rxbuslearn.scan.androidzxing.OnScannerCompletionListener;
import zlk.com.rxbuslearn.scan.androidzxing.ScannerOptions;
import zlk.com.rxbuslearn.scan.androidzxing.ScannerView;
import zlk.com.rxbuslearn.scan.androidzxing.common.Scanner;

/**
 * Created by ice on 2017/12/18 0018.
 * this is a xxx for
 */

public class AndroidZXingActivity extends AppCompatActivity implements OnScannerCompletionListener {

    private ScannerView mScannerView;

    private boolean mReturnScanResult = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_zxing);

        mScannerView = (ScannerView) findViewById(R.id.scanner_view);
        mScannerView.setOnScannerCompletionListener(this);

        ScannerOptions.Builder builder = new ScannerOptions.Builder();
        builder.setFrameSize(256, 256)
                .setFrameCornerLength(22)
                .setFrameCornerWidth(2)
                .setFrameCornerColor(0xff06c1ae)
                .setFrameCornerInside(true)

//                .setLaserLineColor(0xff06c1ae)
//                .setLaserLineHeight(18)

//                .setLaserStyle(ScannerOptions.LaserStyle.RES_LINE,R.mipmap.wx_scan_line)

//                .setLaserStyle(ScannerOptions.LaserStyle.RES_GRID, R.mipmap.zfb_grid_scan_line)//网格图
//                .setFrameCornerColor(0xFF26CEFF)//支付宝颜色

                .setScanFullScreen(true)

//                .setFrameHide(false)
                .setFrameCornerHide(false)
//                .setLaserMoveFullScreen(false)

//                .setViewfinderCallback(new ScannerOptions.ViewfinderCallback() {
//                    @Override
//                    public void onDraw(Canvas canvas, Rect frame) {
//                        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.connect_logo);
//                        canvas.drawBitmap(bmp, frame.right / 2, frame.top - bmp.getHeight(), null);
//                    }
//                })

                .setScanMode(Scanner.ScanMode.PRODUCT_MODE)
                .setTipText("请联系其它已添加该设备用户获取二维码")
                .setTipTextSize(19)
                .setTipTextColor(getResources().getColor(R.color.colorAccent))
//                .setCameraZoomRatio(2)
        ;

        mScannerView.setScannerOptions(builder.build());
    }

    @Override
    protected void onResume() {
        mScannerView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }

    @Override
    public void OnScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        if (rawResult == null) {
            Toast.makeText(this, "未发现二维码", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (mReturnScanResult) {
            onReturnScanResult(rawResult);
            return;
        }
    }

    private void onReturnScanResult(Result rawResult) {
        Intent intent = getIntent();
        intent.putExtra(Scanner.Scan.RESULT, rawResult.getText());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}

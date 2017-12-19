package zlk.com.rxbuslearn.scan.androidzxing.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
                .setLaserLineCenterVertical(true)
                .setOtherFrameBgRes(ContextCompat.getColor(this, R.color.viewfinder_mask))
                .setLaserLineColor(ContextCompat.getColor(this, R.color.orange_light))
                .setLaserLineHeight(getResources().getDimensionPixelSize(R.dimen.dp6))
                .setScanFullScreen(false)
                .setFrameCornerHide(false)
                .setScanMode(Scanner.ScanMode.ONE_D_MODE)
                .setTipText("请联系其它已添加该设备用户获取二维码")
                .setTipTextSize(19)
                .setTipTextColor(getResources().getColor(R.color.colorAccent));

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
    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
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

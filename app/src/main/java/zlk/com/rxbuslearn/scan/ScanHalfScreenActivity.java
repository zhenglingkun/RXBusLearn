package zlk.com.rxbuslearn.scan;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import zlk.com.rxbuslearn.R;

/**
 * Created by ice on 2017/12/15 0015.
 * this is a xxx for
 */

public class ScanHalfScreenActivity extends AppCompatActivity {

    private static final String TAG = ScanHalfScreenActivity.class.getSimpleName();
//    private ZXingView mQRCodeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // 强制竖屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            StatusBarColorManager statusBarColorManager = new StatusBarColorManager(this, true);
//            statusBarColorManager.setStatusBarResource(R.color.title_bar_color); //colorPrimary
        } else {
            // 无标题
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_half);

//        mQRCodeView = (ZXingView) findViewById(R.id.zv_scan_half);
//        mQRCodeView.setDelegate(new QRCodeView.Delegate() {
//            @Override
//            public void onScanQRCodeSuccess(String result) {
//                Log.i(TAG, "onScanQRCodeSuccess: result = " + result);
//                //成功逻辑处理
//            }
//
//            @Override
//            public void onScanQRCodeOpenCameraError() {
//                //错误逻辑处理
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
//        mQRCodeView.startCamera();
//        mQRCodeView.showScanRect();
//        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
//        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        mQRCodeView.onDestroy();
        super.onDestroy();
    }

}

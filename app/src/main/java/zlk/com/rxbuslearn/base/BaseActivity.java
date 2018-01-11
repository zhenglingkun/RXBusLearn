package zlk.com.rxbuslearn.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import zlk.com.rxbuslearn.R;
import zlk.com.rxbuslearn.base.dialog.RemindDialog;
import zlk.com.rxbuslearn.util.CameraUtil;

/**
 * Created by ice on 2017/11/16 0016.
 * this is a xxx for
 */

public class BaseActivity extends AppCompatActivity {

    /**
     * 检测相机权限是否打开，并跳转到指定 Activity
     *
     * @param cls         要跳转的扫码界面
     * @param requestCode
     * @param b
     */
    public void scanCheck(Class cls, int requestCode, Bundle b) {
        if (!CameraUtil.cameraIsCanUse()) {
            RemindDialog dialog = new RemindDialog();
            dialog.setRemindInfo(getString(R.string.msg_camera_framework_bug),
                    getString(R.string.app_name));
            dialog.setCanBack(false);
            dialog.setCanOutDisable(false);
            dialog.show(getSupportFragmentManager(), "CameraDialog");
        } else {
            openActivityForResult(cls, requestCode, b);
        }
    }

    protected void openActivityForResult(Class<?> activityClass, int requestCode, @Nullable Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onResume() {
        super.onResume();
        FrontApplication.setIsAppRunningFront(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        FrontApplication.setIsAppRunningFront(false);
    }

}

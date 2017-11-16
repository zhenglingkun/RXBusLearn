package zlk.com.rxbuslearn.notice;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import zlk.com.rxbuslearn.R;
import zlk.com.rxbuslearn.base.dialog.OnPositiveButtonClick;
import zlk.com.rxbuslearn.base.dialog.RemindDialog;

/**
 * Created by ice on 2017/11/16 0016.
 * this is a xxx for 检测应用通知栏权限，跳转到应用设置界面
 */

public class NoticeActivity extends AppCompatActivity {

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        findViewById(R.id.bt_notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotificationEnabled(NoticeActivity.this)) { // 检测用户是否开启了通知栏权限
                    // anything
                } else {
                    RemindDialog dialog = new RemindDialog();
                    dialog.setCanOutDisable(false);
                    dialog.setRemindInfo("通知栏权限未开启",
                            "请将应用通知功能打开",
                            "去设置");
                    dialog.setOnPositiveButtonClick(new OnPositiveButtonClick() {
                        @Override
                        public void onConfirm(Button btn) {
                            intent2Setting();
                        }
                    });
                    dialog.show(getSupportFragmentManager(), "DialogSetNotice");
                }
            }
        });
    }

    @SuppressLint("NewApi")
    private boolean isNotificationEnabled(Context context) {
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass;
      /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW,
                    Integer.TYPE,
                    Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void intent2Setting() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }

}

package zlk.com.rxbuslearn.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by ice on 2017/11/16 0016.
 * this is a xxx for
 */

public class BaseFragment extends Fragment {

    /**
     * 检测相机权限是否打开，并跳转到指定 Activity
     *
     * @param cls         要跳转的扫码界面
     * @param requestCode
     * @param b
     */
    public void scanCheck(Class cls, int requestCode, Bundle b) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).scanCheck(cls, requestCode, b);
        }
    }

}

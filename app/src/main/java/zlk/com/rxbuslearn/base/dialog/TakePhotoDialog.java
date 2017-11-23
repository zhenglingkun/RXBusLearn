package zlk.com.rxbuslearn.base.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import zlk.com.rxbuslearn.R;

/**
 * Created by xiazj on 2017/1/5.
 */

public class TakePhotoDialog extends DialogFragment {

    private TextView tvCancel;
    private TextView takephoto_camera;
    private TextView takephoto_xiangce;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全屏
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 加载title布局
        View rootView = inflater.inflate(R.layout.layout_pic_dialog, container);
        initView(rootView);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow(); //得到对话框
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.gravity = Gravity.BOTTOM; //设置重力
            window.setAttributes(wl);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void initView(View rootView) {
        tvCancel = (TextView) rootView.findViewById(R.id.tv_cancel);

        tvCancel.setOnClickListener(mOnclickListener);
        takephoto_xiangce = (TextView) rootView.findViewById(R.id.takephoto_xiangce);

        takephoto_xiangce.setOnClickListener(mOnclickListener);
        takephoto_camera = (TextView) rootView.findViewById(R.id.takephoto_camera);

        takephoto_camera.setOnClickListener(mOnclickListener);

    }


    private View.OnClickListener mOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cancel:
                    TakePhotoDialog.this.dismiss();
                    break;
                case R.id.takephoto_camera:
                    TakePhotoDialog.this.dismiss();
                    if (mOnSelectClickListener != null) {
                        mOnSelectClickListener.onCameraClick();
                    }
                    break;
                case R.id.takephoto_xiangce:
                    TakePhotoDialog.this.dismiss();
                    if (mOnSelectClickListener != null) {
                        mOnSelectClickListener.onPhotoClick();
                    }
                    break;
            }
        }
    };

    public interface OnSelectClickListener {
        void onCameraClick();

        void onPhotoClick();
    }

    private OnSelectClickListener mOnSelectClickListener;

    public void setOnSelectClickListener(OnSelectClickListener onSelectClickListener) {
        mOnSelectClickListener = onSelectClickListener;
    }
}
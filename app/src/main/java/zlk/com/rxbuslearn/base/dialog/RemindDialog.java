package zlk.com.rxbuslearn.base.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import zlk.com.rxbuslearn.R;

/**
 * Created by xiazj on 2016/12/22.
 */

public class RemindDialog extends DialogFragment {

    private String title;
    private String info;
    private String confirm = "确定";
    private boolean canBack = true;
    private boolean canOutDisable = true;
    private boolean hideCancel = false;

    private OnNegativeButtonClick mOnNegativeButtonClick;
    private OnPositiveButtonClick mOnPositiveButtonClick;

    /**
     * 初始化 只有title
     *
     * @param title
     */
    public void setRemindTitle(String title) {
        this.title = title;
    }

    /**
     * 初始化 只有info
     *
     * @param info
     */
    public void setRemindInfo(String info) {
        this.info = info;
    }

    /**
     * 初始化 title和info
     *
     * @param info
     * @param title
     */
    public void setRemindInfo(String info, String title) {
        this.info = info;
        this.title = title;
    }

    /**
     * 初始化 title和info
     *
     * @param info
     * @param title
     */
    public void setRemindInfo(String info, String title, String confirm) {
        this.info = info;
        this.title = title;
        this.confirm = confirm;
    }

    /**
     * 能否点击外部空白消失
     *
     * @param canBack 默认可以
     */
    public void setCanBack(boolean canBack) {
        this.canBack = canBack;
    }

    public void setHideCancel(boolean hideCancel) {
        this.hideCancel = hideCancel;
    }

    /**
     * 能否点系统返回键消失
     *
     * @param canOutDisable 默认可以
     */
    public void setCanOutDisable(boolean canOutDisable) {
        this.canOutDisable = canOutDisable;
    }

    /**
     * 设置取消回调
     *
     * @param onNegativeButtonClick
     */
    public void setOnNegativeButtonClick(OnNegativeButtonClick onNegativeButtonClick) {
        this.mOnNegativeButtonClick = onNegativeButtonClick;
    }

    /**
     * 设置确定回调
     *
     * @param onPositiveButtonClick
     */
    public void setOnPositiveButtonClick(OnPositiveButtonClick onPositiveButtonClick) {
        this.mOnPositiveButtonClick = onPositiveButtonClick;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.dialog_remind, container);
        findView(rootView);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return rootView;
    }

    private void findView(View rootView) {
        TextView tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        TextView tvInfo = (TextView) rootView.findViewById(R.id.tv_info);

        Button btnConfirm = (Button) rootView.findViewById(R.id.btn_confirm);
        Button btnCancel = (Button) rootView.findViewById(R.id.btn_cancel);

        if (!TextUtils.isEmpty(info)) {
            tvInfo.setText(info);
            tvInfo.setVisibility(View.VISIBLE);
        } else {
            tvInfo.setVisibility(View.GONE);
        }
        btnConfirm.setText(confirm);
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
            tv_title.setVisibility(View.VISIBLE);
        } else {
            tv_title.setVisibility(View.GONE);
        }

        if (hideCancel) {
            btnCancel.setVisibility(View.GONE);
            rootView.findViewById(R.id.line).setVisibility(View.GONE);
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnNegativeButtonClick != null) {
                    RemindDialog.this.dismiss();
                    mOnNegativeButtonClick.onCancel();
                } else {
                    RemindDialog.this.dismiss();
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPositiveButtonClick == null) {
                    RemindDialog.this.dismiss();
                } else {
                    RemindDialog.this.dismiss();
                    mOnPositiveButtonClick.onConfirm((Button) v);
                }
            }
        });
        getDialog().setCanceledOnTouchOutside(canOutDisable);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return !canBack;
                }
                return false;
            }
        });
    }

}
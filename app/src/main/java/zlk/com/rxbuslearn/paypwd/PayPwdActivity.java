package zlk.com.rxbuslearn.paypwd;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import zlk.com.rxbuslearn.R;

/**
 * Created by kun on 2016/7/19.
 */
public class PayPwdActivity extends AppCompatActivity {

    @BindView(R.id.pay_pwd_fl)
    FrameLayout mPayPwdFl;
    @BindView(R.id.pay_pwd_et)
    EditText mPayPwdEt;
    @BindView(R.id.pwd0_tv)
    TextView mPwd0Tv;
    @BindView(R.id.pwd1_tv)
    TextView mPwd1Tv;
    @BindView(R.id.pwd2_tv)
    TextView mPwd2Tv;
    @BindView(R.id.pwd3_tv)
    TextView mPwd3Tv;
    @BindView(R.id.pwd4_tv)
    TextView mPwd4Tv;
    @BindView(R.id.pwd5_tv)
    TextView mPwd5Tv;

    private TextView[] mPwdTvs;
    private StringBuilder mPwdBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pwd);
        ButterKnife.bind(this);

        mPwdBuilder = new StringBuilder();

        mPwdTvs = new TextView[] {
                mPwd0Tv, mPwd1Tv, mPwd2Tv, mPwd3Tv, mPwd4Tv, mPwd5Tv
        };

        mPayPwdFl.setBackgroundColor(Color.GRAY);

        mPayPwdEt.setOnKeyListener(new PwdKeyListener());
        mPayPwdEt.addTextChangedListener(new PwdTextWatcher());
        requestInput(mPayPwdEt, false);
    }

    private class PwdTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() == 0) {
                return;
            }
            if (mPwdBuilder.length() < 6) {
                mPwdBuilder.append(s.toString());
                setTextValue();
            }
            s.delete(0, s.length());
        }
    }

    private class PwdKeyListener implements View.OnKeyListener {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL
                    && event.getAction() == KeyEvent.ACTION_UP) {
                delTextValue();
                return true;
            }
            return false;
        }
    }

    private void setTextValue() {
        String str = mPwdBuilder.toString();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            mPwdTvs[i].setText(String.valueOf(mPwdBuilder.charAt(i)));
        }
        if (len == 6) {
            requestInput(mPayPwdEt, true);
        }
    }

    private void delTextValue() {
        String str = mPwdBuilder.toString();
        int len = str.length();
        if (len == 0) {
            return;
        }
        if (len > 0 && len <= 6) {
            mPwdBuilder.delete(len - 1, len);
        }
        String str1 = mPwdBuilder.toString();
        int len1 = str1.length();
        for (int i = len - 1; i >= len1; i--) {
            mPwdTvs[i].setText("");
        }
    }

    /**
     * 控制输入键盘的弹出和隐藏
     *
     * @param view   请求输入法的 view
     * @param isHide true:隐藏；false:显示
     */
    private void requestInput(final View view, final boolean isHide) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                handleInputMethod(view, isHide);
            }
        }, 100);
    }

    /**
     * 控制输入键盘的弹出和隐藏
     *
     * @param view   请求输入法的 view
     * @param isHide true:隐藏；false:显示
     */
    private void handleInputMethod(View view, boolean isHide) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isHide) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

}

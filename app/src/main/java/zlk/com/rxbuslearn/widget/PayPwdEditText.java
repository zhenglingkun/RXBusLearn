package zlk.com.rxbuslearn.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.EditText;

import zlk.com.rxbuslearn.R;

/**
 * Created by kun on 2016/7/18.
 */
public class PayPwdEditText extends EditText {

    private int pwdLength = 1; // 密码的位数
    private static final int defaultSplitLineWidth = 3;
    private int textLength;// 输入密码文本的长度
    private float borderWidth = 1; // 密码框边框线的宽
    private int borderColor = 0xFFCCCCCC; // 边框的颜色
    private int passwordColor = 0xFFCCCCCC; // 密码文字的颜色,即点的颜色
    private int pwdRectBgColor = Color.WHITE; // 密码框的背景色
    private float passwordWidth = 8;// 密码文字的宽度,即点的宽度
    private float pwdRectWidth = 40; // 密码框的宽度
    private float pwdRectHeight = 40; // 密码框的长度，与宽度值相同
    private float pwdRectSpace = 0; // 两个密码输入框之间的空间大小，即间隙

    private Paint passwordPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PayPwdEditText(Context context) {
        super(context);
    }

    public PayPwdEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        setMaxLines(1);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        borderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, borderWidth, dm);
        passwordWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, passwordWidth, dm);
        pwdRectWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pwdRectWidth, dm);
        pwdRectSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pwdRectSpace, dm);
        pwdLength = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pwdLength, dm);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PayPwdEditText, 0, 0);
        borderColor = a.getColor(R.styleable.PayPwdEditText_borderColor, borderColor);
        pwdRectBgColor = a.getColor(R.styleable.PayPwdEditText_pwdRectBgColor, pwdRectBgColor);
        passwordColor = a.getColor(R.styleable.PayPwdEditText_passwordColor, passwordColor);
        borderWidth = a.getDimension(R.styleable.PayPwdEditText_borderWidth, borderWidth);
        passwordWidth = a.getDimension(R.styleable.PayPwdEditText_passwordWidth, passwordWidth);
        pwdLength = a.getInt(R.styleable.PayPwdEditText_pwdLength, pwdLength);
        pwdRectWidth = getWidth() / pwdLength;
        pwdRectWidth = a.getDimension(R.styleable.PayPwdEditText_pwdRectWidth, pwdRectWidth);
        pwdRectSpace = a.getDimension(R.styleable.PayPwdEditText_pwdRectSpace, pwdRectSpace);
        a.recycle();

        pwdRectHeight = pwdRectWidth;

        borderPaint.setStrokeWidth(defaultSplitLineWidth);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);
        passwordPaint.setStrokeWidth(passwordWidth);
        passwordPaint.setAntiAlias(true);
        passwordPaint.setStyle(Paint.Style.FILL);
        passwordPaint.setColor(passwordColor);
    }

    public PayPwdEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        RectF rect = new RectF(0, 0, getWidth(), getHeight());
        borderPaint.setColor(Color.GRAY);
        canvas.drawRect(rect, borderPaint);
//
//        int width = getWidth();
//        float totalWidth = (pwdRectWidth + pwdRectSpace) * pwdLength;
//        if (totalWidth > getWidth()) {
//            totalWidth = getWidth();
//        }
//        float start = (width - totalWidth) / 2;
//
//        float x1 = start;
//        borderPaint.setColor(pwdRectBgColor);
//        borderPaint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(x1, 0, pwdRectWidth, pwdRectHeight, borderPaint);
//        borderPaint.setColor(borderColor);
//        borderPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(x1, 0, pwdRectWidth, pwdRectHeight, borderPaint);

        // 分割线
        for (int i = 0; i < pwdLength; i++) {
            float x = pwdRectWidth * i + pwdRectSpace;
            borderPaint.setColor(pwdRectBgColor);
            borderPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(x, 0, pwdRectWidth * (i + 1), pwdRectHeight, borderPaint);
            borderPaint.setColor(borderColor);
            borderPaint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(x, 0, pwdRectWidth * (i + 1), pwdRectHeight, borderPaint);
        }

        float cx = (pwdRectWidth + pwdRectSpace) / 2;
        float cy = pwdRectWidth / 2;
        float half = (pwdRectWidth - pwdRectSpace) / 2;
        for(int i = 0; i < textLength; i++) {
            canvas.drawCircle(cx, cy, passwordWidth, passwordPaint);
            cx = ((pwdRectWidth + pwdRectSpace) * (i + 2)) / 2 + half * (i + 1);
        }

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textLength = text.toString().length();
        invalidate();
    }
}

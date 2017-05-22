package zlk.com.rxbuslearn.swipetoloadlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.zlk.swipetoloadlayout.SwipeRefreshHeaderLayout;

/**
 * Created by ice on 2017/5/22 0022.
 * this is a xxx for
 */

public class LearnHeader extends SwipeRefreshHeaderLayout {

    public static final String TAG = LearnHeader.class.getSimpleName();

//    private TextView mLearnTv;
//    private TextView mLearn1Tv;

//    private int mHeaderHeight;

    public LearnHeader(Context context) {
        this(context, null);
    }

    public LearnHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LearnHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        mHeaderHeight = getResources().getDimensionPixelOffset(R.dimen.refresh_header_height);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Log.i(TAG, "onFinishInflate: ");

//        mLearnTv = (TextView) findViewById(R.id.learn_tv);
//        mLearn1Tv = (TextView) findViewById(R.id.learn1_tv);
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh: ");
    }

    @Override
    public void onPrepare() {
//        mLearnTv.setText("库存总量\n8900");
//        mLearn1Tv.setText("库存总量\n8900");
        Log.i(TAG, "onPrepare: ");
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            Log.d(TAG, "onMove() called with: y = [" + y + "], isComplete = [" + isComplete + "], automatic = [" + automatic + "]");
        }
    }

    @Override
    public void onRelease() {
        Log.i(TAG, "onRelease: ");
    }

    @Override
    public void onComplete() {
        Log.i(TAG, "onComplete: ");

//        mLearnTv.setText("库存总量\n8901");
//        mLearn1Tv.setText("库存总量\n8901");

    }

    @Override
    public void onReset() {
        Log.i(TAG, "onReset: ");
    }
}

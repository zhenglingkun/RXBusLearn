package zlk.com.rxbuslearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 * @author zlk
 * @time 2016/7/6.
 */
public class RxBusActivity extends AppCompatActivity {

    private static final String TAG = RxBusActivity.class.getSimpleName();
    private Subscription mSubscription;

    @BindView(R.id.back_bt)
    Button mBackBt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);
        ButterKnife.bind(this);

//        mSubscription = RxBus.getDefault()
//                .toObservable(TestEvent.class)
//                .subscribe(new Action1<TestEvent>() {
//                    @Override
//                    public void call(TestEvent testEvent) {
//                        Log.i(TAG, "call: name = " + testEvent.getName());
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.wtf(TAG, "call: ", throwable);
//                    }
//                });

//        mSubscription = RxBus.getDefault()
//                .toObservable().subscribe(new Action1<Object>() {
//                    @Override
//                    public void call(Object o) {
//                        if (o instanceof TestEvent) {
//                            Log.i(TAG, "call: name = " + ((TestEvent) o).getName());
//                        } else {
//                            Log.i(TAG, "call: o = " + o.toString());
//                        }
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.e(TAG, "call: ", throwable);
//                    }
//                });

        mSubscription = RxBus.getDefault()
                .toObservable(TestEvent.class)
                .subscribe(new Action1<TestEvent>() {
                    @Override
                    public void call(TestEvent testEvent) {
                        Log.i(TAG, "call: name = " + testEvent.getName());
                    }
                });
    }

    @OnClick(R.id.back_bt)
    public void onClick() {
//        RxBus.getDefault().send("This a String");

//        RxBus.getDefault().send(new TestEvent("Javac"));
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        super.onDestroy();
    }
}

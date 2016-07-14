package zlk.com.rxbuslearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Subscription mSubscription;

    @BindView(R.id.intent_bt)
    Button mIntentBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Observable.just("123", "300")
                .last()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, "call: s = " + s);
                    }
                });
    }

    @OnClick(R.id.intent_bt)
    public void onClick() {
        RxBus.getDefault().send(new TestEvent("Java"));
        startActivity(new Intent(MainActivity.this, RxBusActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        mSubscription = RxBus.getDefault().toObservable(Object.class).subscribe();
    }

    @Override
    protected void onDestroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        super.onDestroy();
    }
}

package zlk.com.rxbuslearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSubscription = RxBus.getDefault()
                .toObservable(String.class)
                .subscribe(new Action1<String>() {
                               @Override
                               public void call(String s) {
                                   Log.i(TAG, "call: back string is " + s);
                                   TextView view = (TextView) findViewById(R.id.back_text_tv);
                                   view.setText(s);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.wtf(TAG, "call: ", throwable);
                            }
                        });
    }

    @Override
    protected void onDestroy() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        super.onDestroy();
    }

    @OnClick(R.id.intent_bt)
    public void onClick() {
        startActivity(new Intent(MainActivity.this, RxBusActivity.class));
    }
}

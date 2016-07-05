package zlk.com.rxbuslearn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zlk
 * @time 2016/7/6.
 */
public class RxBusActivity extends AppCompatActivity {

    @BindView(R.id.back_bt)
    Button mBackBt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_bt)
    public void onClick() {
        RxBus.getDefault().send("This a String");
        onBackPressed();
    }
}

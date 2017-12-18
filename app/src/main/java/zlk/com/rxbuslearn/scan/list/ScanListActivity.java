package zlk.com.rxbuslearn.scan.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import zlk.com.rxbuslearn.R;
import zlk.com.rxbuslearn.scan.ScanHalfScreenActivity;
import zlk.com.rxbuslearn.scan.ScanViewActivity;

/**
 * Created by ice on 2017/12/15 0015.
 * this is a xxx for
 */

public class ScanListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_list);
        findViewById(R.id.bt_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScanListActivity.this, ScanViewActivity.class));
            }
        });
        findViewById(R.id.bt_half).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScanListActivity.this, ScanHalfScreenActivity.class));
            }
        });
    }
}

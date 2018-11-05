package zlk.com.rxbuslearn.wgallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import zlk.com.rxbuslearn.R;

/**
 * 首页。
 *
 * @author wuzhen
 * @version Version 1.0, 2016-05-10
 */
public class MainWGalleryActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wgallery);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startActivity(new Intent(MainWGalleryActivity.this, AllAty.class));
                break;

            case R.id.btn2:
//                startActivity(new Intent(MainWGalleryActivity.this, WithWGalleryAdapterActivity.class));
                break;

            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

//    @Override
//    protected boolean isDisplayHomeAsUp() {
//        return false;
//    }
}

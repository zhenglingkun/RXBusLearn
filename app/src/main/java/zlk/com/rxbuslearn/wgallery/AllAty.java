package zlk.com.rxbuslearn.wgallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.zlk.wgallery.WGallery;

import zlk.com.rxbuslearn.R;
import zlk.com.rxbuslearn.wgallery.adapter.WGalleryUrlAdapter;

/**
 * Created by ice on 2018/9/18 0018.
 * this is a xxx for
 */
public class AllAty extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    private String[] mImgUrl = new String[]{
            "http://www.huihuokj.com/www/imgs/tg1.png",
            "http://www.huihuokj.com/www/imgs/tg2.png",
            "http://www.huihuokj.com/www/imgs/tg3.png",
            "http://www.huihuokj.com/www/imgs/tg4.png",
            "http://www.huihuokj.com/www/imgs/tg5.png",
            "http://www.huihuokj.com/www/imgs/tg6.png"
    };

    WGallery gallery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wgallery);
        gallery = findViewById(R.id.wgallery);
        gallery.setAdapter(new WGalleryUrlAdapter(mImgUrl));

        RadioGroup rg = findViewById(R.id.rg);
        rg.check(R.id.rbtn3);
        rg.setOnCheckedChangeListener(this);

        ((SeekBar) findViewById(R.id.seekbar1)).setOnSeekBarChangeListener(this);
        ((SeekBar) findViewById(R.id.seekbar2)).setOnSeekBarChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rbtn1:
                gallery.setScalePivot(WGallery.SCALE_PIVOT_CENTER);
                break;

            case R.id.rbtn2:
                gallery.setScalePivot(WGallery.SCALE_PIVOT_TOP);
                break;

            case R.id.rbtn3:
                gallery.setScalePivot(WGallery.SCALE_PIVOT_BOTTOM);
                break;

            default:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.seekbar1:
                gallery.setSelectedScale(0.8f + 0.1f * i);
                break;

            case R.id.seekbar2:
                gallery.setUnSelectedAlpha(i * 1.f / 10);
                break;

            default:
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

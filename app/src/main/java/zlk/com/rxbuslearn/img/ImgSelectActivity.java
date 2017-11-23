package zlk.com.rxbuslearn.img;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import zlk.com.rxbuslearn.R;
import zlk.com.rxbuslearn.base.BaseSelectImgActivity;

/**
 * Created by ice on 2017/11/23 0023.
 * this is a xxx for
 */

public class ImgSelectActivity extends BaseSelectImgActivity {

    private ImageView mIvSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_img);
        mIvSelect = (ImageView) findViewById(R.id.iv_select);
        findViewById(R.id.bt_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicDialog();
            }
        });
        findViewById(R.id.bt_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvSelect.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });
    }

    @Override
    protected void initShowImgWH() {
        mShowImgWidth = getResources().getDimensionPixelSize(R.dimen.dp130);
        mShowImgHeight = getResources().getDimensionPixelSize(R.dimen.dp130);
    }

    @Override
    protected void showImg(Bitmap bitmap) {
        mIvSelect.setImageBitmap(bitmap);
    }
}

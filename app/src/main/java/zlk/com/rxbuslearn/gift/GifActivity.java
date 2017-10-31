package zlk.com.rxbuslearn.gift;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import zlk.com.rxbuslearn.R;

/**
 * Created by ice on 2017/10/9 0009.
 * this is a xxx for
 */

public class GifActivity extends AppCompatActivity {

    private static final String TAG = GifActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        GifImageView gv = (GifImageView) findViewById(R.id.gv_test1);
        try {
            GifDrawable gifDrawable = new GifDrawable(getAssets(), "gif_3.gif");
            gv.setImageDrawable(gifDrawable);
            gifDrawable.addAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationCompleted(int loopNumber) {
                    Log.d(TAG, "onAnimationCompleted() called with: loopNumber = [" + loopNumber + "]");
                    // to to your code
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
//        GiftView gv = (GiftView) findViewById(R.id.gv_test);
//        gv.setMovieResource(R.raw.gif_3);
    }
}

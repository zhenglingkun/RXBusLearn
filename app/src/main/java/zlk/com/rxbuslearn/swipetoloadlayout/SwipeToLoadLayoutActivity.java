package zlk.com.rxbuslearn.swipetoloadlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.zlk.swipetoloadlayout.OnRefreshListener;
import com.zlk.swipetoloadlayout.SwipeToLoadLayout;

import zlk.com.rxbuslearn.R;

/**
 * Created by ice on 2017/5/22 0022.
 * this is a xxx for
 */

public class SwipeToLoadLayoutActivity extends AppCompatActivity {

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView mRecyclerView;

    private TextView mLearnTv;
    private TextView mLearn1Tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LearnHeader.TAG, "onCreate: ");
        setContentView(R.layout.activity_swipe_to_load_layout);
        Log.i(LearnHeader.TAG, "onCreate: setContentView()");

//        LearnHeader headerView = (LearnHeader) findViewById(R.id.swipe_refresh_header);

        mLearnTv = (TextView) findViewById(R.id.learn_tv);
        mLearn1Tv = (TextView) findViewById(R.id.learn1_tv);
        String str = getString(R.string.learn);
        mLearnTv.setText(String.format(str, 7900));
        mLearn1Tv.setText(String.format(str, 7901));

        mRecyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeToLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoadLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
    }
}

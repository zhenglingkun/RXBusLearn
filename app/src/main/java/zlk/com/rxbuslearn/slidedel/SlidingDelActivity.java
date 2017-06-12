package zlk.com.rxbuslearn.slidedel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import zlk.com.rxbuslearn.R;

/**
 * Created by ice on 2017/6/12 0012.
 * this is a xxx for
 */

public class SlidingDelActivity extends AppCompatActivity implements MyAdapter.IonSlidingViewClickListener {

    private RecyclerView mMainRv;
    private MyAdapter myAdapter;
    private ArrayList<String> date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_del);
        mMainRv = (RecyclerView) findViewById(R.id.rv_main);

        date = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            date.add(i + "");
        }

        mMainRv.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(date, Utils.getScreenWidth(this));
        myAdapter.setIDeleteBtnClickListener(this);
        mMainRv.setAdapter(myAdapter);
        mMainRv.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteBtnClick(View view, int position) {
        myAdapter.removeData(position);
    }
}

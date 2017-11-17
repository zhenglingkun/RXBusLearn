package zlk.com.rxbuslearn.expandablelistview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import zlk.com.rxbuslearn.R;

/**
 * Created by ice on 2017/11/17 0017.
 * this is a xxx for
 */

public class ExpandableListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.elv_test);
        List<First> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            First first = new First();

            first.setName("test" + i);
            List<Second> seconds = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Second second = new Second();
                second.setName("text0" + j);
                seconds.add(second);
            }
            first.setSeconds(seconds);

            data.add(first);
        }
        expandableListView.setAdapter(new ElvTestAdapter(data));
    }

}

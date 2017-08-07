package zlk.com.rxbuslearn.edittextsearch;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import zlk.com.rxbuslearn.R;

/**
 * Created by ice on 2017/8/7 0007.
 * this is a xxx for 实时搜索优化
 */

public class EditSearchActivity extends AppCompatActivity implements OptionSearch.IFinishListener {

    private static final String TAG = EditSearchActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_search);

        final OptionSearch search = new OptionSearch(Looper.myLooper());
        search.setListener(this);

        EditText etSearch = (EditText) findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search.optionSearch(s.toString().trim());
            }
        });

    }

    @Override
    public void getKeyword(String keyword) {
        Log.d(TAG, "getKeyword() called with: keyword = [" + keyword + "]");
        // TODO: 2017/8/7 0007 调用搜索接口
    }
}

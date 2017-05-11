package zlk.com.rxbuslearn;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zlk.com.rxbuslearn.pinnedheaderlistview.CustomerBean;
import zlk.com.rxbuslearn.pinnedheaderlistview.LetterAdapter;
import zlk.com.rxbuslearn.pinnedheaderlistview.LetterBean;
import zlk.com.rxbuslearn.pinnedheaderlistview.PinnedHeaderBean;
import zlk.com.rxbuslearn.pinnedheaderlistview.PinnedHeaderListView;
import zlk.com.rxbuslearn.pinnedheaderlistview.ContactPinnedAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.intent_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        pinnedHeaderLvLearn();
    }

    private void pinnedHeaderLvLearn() {
        final PinnedHeaderListView listView = (PinnedHeaderListView) findViewById(R.id.phlv);

        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout header1 = (LinearLayout) inflator.inflate(R.layout.item_contact_name, null);
        ((TextView) header1.findViewById(R.id.textItem)).setText("HEADER 1");
        LinearLayout header2 = (LinearLayout) inflator.inflate(R.layout.item_contact_name, null);
        ((TextView) header2.findViewById(R.id.textItem)).setText("HEADER 2");

        LinearLayout footer = (LinearLayout) inflator.inflate(R.layout.item_contact_name, null);
        ((TextView) footer.findViewById(R.id.textItem)).setText("FOOTER");

        listView.addHeaderView(header1);
        listView.addHeaderView(header2);
        listView.addFooterView(footer);

        // init section header data
        final List<PinnedHeaderBean> sectionHeader = new ArrayList<>();
        PinnedHeaderBean pinnedHeaderBean;
        String[] letters = getResources().getStringArray(R.array.letters);
        for (int i = 0; i < letters.length; i++) {
            pinnedHeaderBean = new PinnedHeaderBean();
            pinnedHeaderBean.setHeader(letters[i]);
            List<CustomerBean> sectionList = new ArrayList<>();
            CustomerBean custom;
            for (int j = 0; j < i; j++) {
                custom = new CustomerBean();
                custom.setContactName("Section " + j);
                sectionList.add(custom);
            }
            pinnedHeaderBean.setSection(sectionList);
            sectionHeader.add(pinnedHeaderBean);
        }

        final ContactPinnedAdapter sectionedAdapter = new ContactPinnedAdapter();
        listView.setAdapter(sectionedAdapter);
        sectionedAdapter.addData(sectionHeader);

        LetterAdapter mLetterAdapter = new LetterAdapter();
        final List<LetterBean> letterList = new ArrayList<>();
        LetterBean letterBean;
        for (int i = 0, size = letters.length; i < size; i++) {
            letterBean = new LetterBean();
            letterBean.setLetter(letters[i]);
            letterBean.setSection(-1);
            letterList.add(letterBean);
        }
        mLetterAdapter.addData(letterList);
        ListView letterLv = (ListView) findViewById(R.id.letter_lv);
        letterLv.setAdapter(mLetterAdapter);
        letterLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int section = 0;
                LetterBean letter = letterList.get(position);
                for (int i = 0, size = sectionHeader.size(); i < size; i++) {
                    section += 1;
                    if (letter.getLetter().equals(sectionHeader.get(i).getHeader())) {
                        Log.i(TAG, "onItemClick: letter is " + letter.getLetter());
                        Log.i(TAG, "onItemClick: section is " + section);
                        break;
                    }
                    section += sectionHeader.get(i).getSection().size();
                }
                listView.setSelection(section);
            }
        });
    }

}

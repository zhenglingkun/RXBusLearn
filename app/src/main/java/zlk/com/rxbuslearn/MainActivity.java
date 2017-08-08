package zlk.com.rxbuslearn;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import zlk.com.rxbuslearn.audio.AudioActivity;
import zlk.com.rxbuslearn.pinnedheaderlistview.ContactPinnedAdapter;
import zlk.com.rxbuslearn.pinnedheaderlistview.CustomerBean;
import zlk.com.rxbuslearn.pinnedheaderlistview.LetterAdapter;
import zlk.com.rxbuslearn.pinnedheaderlistview.LetterBean;
import zlk.com.rxbuslearn.pinnedheaderlistview.PinnedHeaderBean;
import zlk.com.rxbuslearn.pinnedheaderlistview.PinnedHeaderListView;
import zlk.com.rxbuslearn.util.FirstLetterUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.intent_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AudioActivity.class));
                // intent to SwipeToLoadLayout
//                startActivity(new Intent(MainActivity.this, SwipeToLoadLayoutActivity.class));

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

    /**
     * 获取通讯录联系人
     * @param customerBeanList
     */
    private void initContactList(List<CustomerBean> customerBeanList) {
        //生成ContentResolver对象
        ContentResolver contentResolver = getContentResolver();
        // 获得所有的联系人
        // 在这里我们给query传递进去一个SORT_KEY_PRIMARY。
        // 告诉ContentResolver获得的结果安装联系人名字的首字母有序排列。
        Log.i("contact", "initData: ContactsContract.CommonDataKinds.Phone.CONTENT_URI = " + ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        Log.i("contact", "initData: ContactsContract.CommonDataKinds.Phone.CONTENT_URI = " + ContactsContract.Contacts.CONTENT_URI);
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                ContactsContract.Contacts.SORT_KEY_PRIMARY);
        if (cursor == null) {
            return;
        }

        // 循环遍历
        if (cursor.moveToFirst()) {
            CustomerBean customerBean;

            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int displayNameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            do {
                customerBean = new CustomerBean();

                // 获得联系人的ID
                String contactId = cursor.getString(idColumn);

                // 获得联系人姓名
                String displayName = cursor.getString(displayNameColumn);
                customerBean.setContactName(displayName);
                customerBean.setNamePinyin(FirstLetterUtil.getFirstLetter(displayName.trim()).toUpperCase());

                List<String> phones = new ArrayList<>();
                // 查看联系人有多少个号码，如果没有号码，返回0
                int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (phoneCount > 0) {
                    // 获得联系人的电话号码列表
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + "=" + contactId, null, null);
                    if (phoneCursor == null) {
                        return;
                    }
                    if (phoneCursor != null && phoneCursor.moveToFirst()) {
                        do {
                            //遍历所有的联系人下面所有的电话号码
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phones.add(phoneNumber);
                        } while (phoneCursor.moveToNext());
                    }
                }
                customerBean.setPhones(phones);
                customerBeanList.add(customerBean);
            } while (cursor.moveToNext());
        }
    }

}

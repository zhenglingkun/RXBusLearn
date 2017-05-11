package zlk.com.rxbuslearn.pinnedheaderlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import zlk.com.rxbuslearn.R;

public class ContactPinnedAdapter extends SectionedBaseAdapter {

    private List<PinnedHeaderBean> mSectionHeader;

    @Override
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    @Override
    public int getSectionCount() { // headerCount
        return mSectionHeader != null ? mSectionHeader.size() : 0;
    }

    @Override
    public int getCountForSection(int section) { // section in header count
        return mSectionHeader != null && mSectionHeader.get(section).getSection() != null ? mSectionHeader.get(section).getSection().size() : 0;
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflater.inflate(R.layout.item_contact_name, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText(mSectionHeader.get(section).getSection().get(position).getContactName());
        return layout;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflater.inflate(R.layout.item_contact_letter, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText(mSectionHeader.get(section).getHeader());
        return layout;
    }

    public void addData(List<PinnedHeaderBean> sectionHeader) {
        this.mSectionHeader = sectionHeader;
        notifyDataSetChanged();
    }

}

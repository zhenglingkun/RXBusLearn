package zlk.com.rxbuslearn.pinnedheaderlistview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zlk.com.rxbuslearn.R;

/**
 * Created by ice on 2017/5/10 0010.
 * this is a xxx for
 */

public class LetterAdapter extends BaseAdapter {

    private List<LetterBean> mData = new ArrayList<>();

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_letter, null);

            holder.letterTv = (TextView) convertView.findViewById(R.id.letter_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.letterTv.setText(mData.get(position).getLetter());

        return convertView;
    }

    public void addData(List<LetterBean> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        public TextView letterTv;
    }
}

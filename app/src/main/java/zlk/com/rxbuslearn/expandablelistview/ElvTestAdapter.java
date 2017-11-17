package zlk.com.rxbuslearn.expandablelistview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zlk.com.rxbuslearn.R;

/**
 * Created by ice on 2017/11/17 0017.
 * this is a xxx for
 */

public class ElvTestAdapter extends BaseExpandableListAdapter {

    private List<First> mData;

    public ElvTestAdapter(List<First> data) {
        mData = data == null ? new ArrayList<First>() : data;
    }

    //        获取分组的个数
    @Override
    public int getGroupCount() {
        return mData.size();
    }

    //        获取指定分组中的子选项的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return mData.get(groupPosition).getSeconds().size();
    }

    //        获取指定的分组数据
    @Override
    public Object getGroup(int groupPosition) {
        return mData.get(groupPosition);
    }

    //        获取指定分组中的指定子选项数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mData.get(groupPosition).getSeconds().get(childPosition);
    }

    //        获取指定分组的ID, 这个ID必须是唯一的
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //        获取子选项的ID, 这个ID必须是唯一的
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //        分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们。
    @Override
    public boolean hasStableIds() {
        return true;
    }

    //        获取显示指定分组的视图
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        FirstHolder holder;
        if (convertView == null) {
            holder = new FirstHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_first, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_test_first);
            convertView.setTag(holder);
        } else {
            holder = (FirstHolder) convertView.getTag();
        }

        holder.tvName.setText(mData.get(groupPosition).getName());
        if (isExpanded) {
            holder.tvName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.xuanzhedagou_icon, 0, R.mipmap.quanbufenlei_jiantou_open, 0);
        } else {
            holder.tvName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.xuanzhedagou_icon, 0, R.mipmap.quanbufenlei_jiantou_close, 0);
        }

        return convertView;
    }

    //        获取显示指定分组中的指定子选项的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SecondHolder holder;
        if (convertView == null) {
            holder = new SecondHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_second, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_test_second);
            convertView.setTag(holder);
        } else {
            holder = (SecondHolder) convertView.getTag();
        }

        holder.tvName.setText(mData.get(groupPosition).getSeconds().get(childPosition).getName());
        holder.tvName.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.xuanzhedagou_icon, 0, 0, 0);

        return convertView;
    }

    //        指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class FirstHolder {
        TextView tvName;
    }

    static class SecondHolder {
        TextView tvName;
    }

}

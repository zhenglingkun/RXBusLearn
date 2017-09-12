package zlk.com.rxbuslearn.recycler;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedHashSet;

/**
 * @author zlk
 * @time 2017/9/13.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    private final LinkedHashSet<Integer> childClickViewIds = new LinkedHashSet();
    private final LinkedHashSet<Integer> itemChildLongClickViewIds = new LinkedHashSet();

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public static BaseViewHolder createViewHolder(View itemView) {
        BaseViewHolder holder = new BaseViewHolder(itemView);
        return holder;
    }

    public static BaseViewHolder createViewHolder(ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        BaseViewHolder holder = new BaseViewHolder(itemView);
        return holder;
    }

    public LinkedHashSet<Integer> getChildClickViewIds() {
        return childClickViewIds;
    }

    public LinkedHashSet<Integer> getItemChildLongClickViewIds() {
        return itemChildLongClickViewIds;
    }

    public BaseViewHolder addOnClickListener(int viewId) {
        this.childClickViewIds.add(Integer.valueOf(viewId));
        return this;
    }

    public BaseViewHolder addOnLongClickListener(int viewId) {
        this.itemChildLongClickViewIds.add(Integer.valueOf(viewId));
        return this;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public BaseViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public BaseViewHolder setTextColor(int viewId, int resColor) {
        TextView tv = getView(viewId);
        tv.setTextColor(ContextCompat.getColor(tv.getContext(), resColor));
        return this;
    }

    public BaseViewHolder setBackgroundRes(int viewId, int bgRes) {
        getView(viewId).setBackgroundResource(bgRes);
        return this;
    }

    public BaseViewHolder setBackgroundColor(int viewId, int bgRes) {
        View view = getView(viewId);
        view.setBackgroundColor(ContextCompat.getColor(view.getContext(), bgRes));
        return this;
    }

    public BaseViewHolder setVisible(int viewId, int visible) {
        getView(viewId).setVisibility(visible);
        return this;
    }

}

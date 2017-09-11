package zlk.com.rxbuslearn.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.LinkedHashSet;

/**
 * Created by ice on 2017/9/11 0011.
 * this is a xxx for
 */

class BaseViewHolder extends RecyclerView.ViewHolder {

    private final LinkedHashSet<Integer> childClickViewIds = new LinkedHashSet();
    private final LinkedHashSet<Integer> itemChildLongClickViewIds = new LinkedHashSet();

    public LinkedHashSet<Integer> getChildClickViewIds() {
        return childClickViewIds;
    }

    public LinkedHashSet<Integer> getItemChildLongClickViewIds() {
        return itemChildLongClickViewIds;
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder addOnClickListener(int viewId) {
        this.childClickViewIds.add(Integer.valueOf(viewId));
        return this;
    }

    public BaseViewHolder addOnLongClickListener(int viewId) {
        this.itemChildLongClickViewIds.add(Integer.valueOf(viewId));
        return this;
    }
}

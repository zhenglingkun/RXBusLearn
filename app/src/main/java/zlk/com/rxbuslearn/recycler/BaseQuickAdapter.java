package zlk.com.rxbuslearn.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by ice on 2017/9/11 0011.
 * this is a xxx for
 */

class BaseQuickAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public int getHeaderLayoutCount() {
        return 0;
    }
}

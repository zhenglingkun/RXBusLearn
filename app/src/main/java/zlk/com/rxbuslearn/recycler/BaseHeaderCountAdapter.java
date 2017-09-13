package zlk.com.rxbuslearn.recycler;

import android.support.v7.widget.RecyclerView;

/**
 * @author zlk
 * @time 2017/9/13.
 */

public abstract class BaseHeaderCountAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public abstract int getHeadersCount();

    public abstract int getFootersCount();

}

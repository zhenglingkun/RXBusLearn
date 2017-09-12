package zlk.com.rxbuslearn.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends BaseHeaderCountAdapter {

    protected List<T> mData;
    private int mResLayoutId;

    public BaseRecyclerAdapter(List<T> data) {
        this(0, data == null ? new ArrayList<T>() : data);
    }

    public BaseRecyclerAdapter(int resLayoutId, List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        this.mResLayoutId = resLayoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = null;
        if (mResLayoutId != 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(mResLayoutId, parent, false);
            baseViewHolder = new BaseViewHolder(view);
        }
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        convert(holder, mData.get(position));
    }

    protected abstract void convert(BaseViewHolder holder, T t);

    @Override
    public int getItemCount() {
        int size = mData == null ? 0 : mData.size();
        return size;
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    public void addDataSetToStart(List<T> list) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(0, list);
        notifyDataSetChanged();
    }

    public void addDataSetToEnd(List<T> list) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void resetDataSet(List<T> list) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getDataSet() {
        return mData;
    }

    @Override
    public int getHeadersCount() {
        return 0;
    }

    @Override
    public int getFootersCount() {
        return 0;
    }
}

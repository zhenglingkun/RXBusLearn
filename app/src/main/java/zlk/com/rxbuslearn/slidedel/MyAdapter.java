package zlk.com.rxbuslearn.slidedel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zlk.com.rxbuslearn.R;

/**
 * Created by jiangzn on 17/1/1.
 */

public class MyAdapter extends RecyclerView.Adapter implements SlidingButtonView.IonSlidingButtonListener {

    private IonSlidingViewClickListener mIDeleteBtnClickListener;

    private List<String> mData = new ArrayList<>();
    private SlidingButtonView mMenu = null;
    private int mContentWidth;

    public MyAdapter(ArrayList<String> date, int contentWidth) {
        this.mData = date;
        this.mContentWidth = contentWidth;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.slidingButtonView.setSlidingButtonListener(MyAdapter.this);

        viewHolder.textView.setText(mData.get(position));

        //设置内容布局的宽为屏幕宽度
        viewHolder.layout_content.getLayoutParams().width = mContentWidth;
        viewHolder.layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIDeleteBtnClickListener != null) {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onItemClick(v, n);
                }
            }
        });

        viewHolder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIDeleteBtnClickListener != null) {
                    int n = holder.getLayoutPosition();
                    mIDeleteBtnClickListener.onDeleteBtnClick(v, n);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 删除菜单打开信息接收
     */
    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 滑动或者点击了Item监听
     *
     * @param slidingButtonView
     */
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if (menuIsOpen()) {
            if (mMenu != slidingButtonView) {
                closeMenu();
            }
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;
    }

    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if (mMenu != null) {
            return true;
        }
        return false;
    }

    public void setIDeleteBtnClickListener(IonSlidingViewClickListener IDeleteBtnClickListener) {
        mIDeleteBtnClickListener = IDeleteBtnClickListener;
    }

    public interface IonSlidingViewClickListener {

        void onItemClick(View view, int position);

        void onDeleteBtnClick(View view, int position);
    }

    public void removeData(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

}

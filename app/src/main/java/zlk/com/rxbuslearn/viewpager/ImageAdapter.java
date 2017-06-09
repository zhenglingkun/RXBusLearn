package zlk.com.rxbuslearn.viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by ice on 2016/12/30 0030.
 * this is a xxx for
 */

public class ImageAdapter extends PagerAdapter {

    private int mChildCount = 0;

    private List<ImageView> mViewList;

    public ImageAdapter(List<ImageView> viewList) {
        mViewList = viewList;
    }

    @Override
    public ImageView instantiateItem(ViewGroup container, int position) {
        ImageView view = mViewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    public void setNewData(List<ImageView> viewList) {
        mViewList.clear();
        mViewList.addAll(viewList);
        notifyDataSetChanged();
    }

}

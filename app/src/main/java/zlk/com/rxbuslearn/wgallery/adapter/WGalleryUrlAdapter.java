package zlk.com.rxbuslearn.wgallery.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zlk.wgallery.IWGalleryAdapter;

import zlk.com.rxbuslearn.R;


/**
 * 继承 IWGalleryAdapter 的 Adapter。
 *
 * @author wuzhen
 * @version Version 1.0, 2016-05-10
 */
public class WGalleryUrlAdapter extends BaseAdapter implements IWGalleryAdapter {

    private String[] mUrl;

    public WGalleryUrlAdapter(String[] url) {
        mUrl = url;
    }

    @Override
    public int getCount() {
        return null == mUrl ? 0 : mUrl.length;
    }

    @Override
    public Object getItem(int position) {
        return mUrl[position];
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sample,
                    parent, false);

            holder.ivShow = convertView.findViewById(R.id.iv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.i("WGallery", "getView: mUrl[" + position + "] " + mUrl[position]);
        Glide.with(convertView.getContext())
                .load(mUrl[position])
                .into(holder.ivShow);
        return convertView;
    }

    public static class ViewHolder {
        public ImageView ivShow;
    }

    @Override
    public int getChangeAlphaViewId() {
        return R.id.border;
    }
}

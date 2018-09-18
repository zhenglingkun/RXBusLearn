package zlk.com.rxbuslearn.wgallery;

import android.widget.BaseAdapter;

import zlk.com.rxbuslearn.wgallery.adapter.SimpleAdapter;

/**
 * 简单示例页面。
 *
 * @author wuzhen
 * @version Version 1.0, 2016-05-10
 */
public class SimpleWGalleryActivity extends BaseWGalleryActivity {

    @Override
    protected BaseAdapter initGalleryAdapter() {
        return new SimpleAdapter();
    }
}

package zlk.com.rxbuslearn.slidedel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zlk.com.rxbuslearn.R;

/**
 * Created by jiangzn on 17/1/1.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView btn_Delete;
    public TextView textView;
    public ViewGroup layout_content;
    public SlidingButtonView slidingButtonView;

    public MyViewHolder(View itemView) {
        super(itemView);
        btn_Delete = (TextView) itemView.findViewById(R.id.tv_delete);
        textView = (TextView) itemView.findViewById(R.id.text);
        layout_content = (ViewGroup) itemView.findViewById(R.id.layout_content);
        slidingButtonView = (SlidingButtonView) itemView;
    }
}

package zlk.com.rxbuslearn.dialoglearn;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zlk.com.rxbuslearn.R;

/**
 * Created by kun on 2016/7/21.
 */
public class DialogLearnActivity extends AppCompatActivity {

    @BindView(R.id.dialog_learn_bt)
    Button mDialogLearnBt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_learn);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.dialog_learn_bt)
    public void onClick() {
        buildNoTitleDialog();
    }

    private void buildNoTitleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_delete_bank_card, null);
        builder.setView(view);
        final Dialog dialog = builder.create();

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);

        view.findViewById(R.id.delete_bank_card_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                // TODO:实现删除银行卡接口调用
            }
        });
        view.findViewById(R.id.cancel_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

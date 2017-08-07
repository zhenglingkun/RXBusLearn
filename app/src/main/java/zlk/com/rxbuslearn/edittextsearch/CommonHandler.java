package zlk.com.rxbuslearn.edittextsearch;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by ice on 2017/8/7 0007.
 * this is a xxx for
 */

public class CommonHandler extends Handler {

    public interface MessageHandler {
        void handleMessage(Message msg);
    }

    private WeakReference<MessageHandler> mMessageHandler;

    public CommonHandler(MessageHandler msgHandler) {
        mMessageHandler = new WeakReference<MessageHandler>(msgHandler);
    }

    public CommonHandler(Looper looper, MessageHandler msgHandler) {
        super(looper);
        mMessageHandler = new WeakReference<MessageHandler>(msgHandler);
    }

    @Override
    public void handleMessage(Message msg) {
        MessageHandler realHandler = mMessageHandler.get();
        if (realHandler != null) {
            realHandler.handleMessage(msg);
        }
    }
}

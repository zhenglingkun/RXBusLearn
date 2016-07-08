package zlk.com.rxbuslearn;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * @author zlk
 * @time 2016/7/5.
 */
public class RxBus {

    private static volatile RxBus mDefaultInstance;

    public RxBus() {
    }

    public static RxBus getDefault() {
        if (mDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }

    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object obj) {
        _bus.onNext(obj);
    }

    /**
     * 根据传递的 eventType 类型返回特定事件类型的被观察者
     *
     * @param eventType 事件类型的实例对像
     * @param <T>       特定事件类型
     * @return 特定事件类型的被观察者
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return _bus.ofType(eventType).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据传递的 eventType 类型返回特定事件类型的被观察者
     *
     * @return 特定事件类型的被观察者
     */
    public Observable<Object> toObservable() {
        return _bus.observeOn(AndroidSchedulers.mainThread());
    }

}

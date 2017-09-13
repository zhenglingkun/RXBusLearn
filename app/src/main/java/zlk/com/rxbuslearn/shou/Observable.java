package zlk.com.rxbuslearn.shou;

/**
 * @author zlk
 * @time 2017/2/15.
 */

public class Observable<T> {

    private OnSubscribe<T> mOnSubscribe;

    public Observable(OnSubscribe<T> onSubscribe) {
        mOnSubscribe = onSubscribe;
    }

    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new Observable<>(onSubscribe);
    }

    public void subscribe(Subscribe<? super T> subscribe) {
        mOnSubscribe.call(subscribe);
    }

}

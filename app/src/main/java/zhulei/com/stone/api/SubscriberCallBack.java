package zhulei.com.stone.api;

import rx.Subscriber;

/**
 * Created by zhulei on 16/7/11.
 */
public class SubscriberCallBack<T> extends Subscriber<T> {

    private final static int HTTP_RESPONSE_OVERDUE = 498;

    private AppClient.ApiCallback<T> apiCallback;

    public SubscriberCallBack(AppClient.ApiCallback<T> apiCallback) {
        this.apiCallback = apiCallback;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        apiCallback.onFail(e.getMessage());
    }

    @Override
    public void onNext(T t) {
        apiCallback.onSuccess(t);
    }
}

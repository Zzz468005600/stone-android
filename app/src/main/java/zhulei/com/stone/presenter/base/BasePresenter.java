package zhulei.com.stone.presenter.base;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import zhulei.com.stone.api.ApiClient;
import zhulei.com.stone.api.ApiStores;

/**
 * author: zhulei
 * date: 2018/7/26
 * blog: http://www.zhuleiblog.com/
 */
public class BasePresenter<V> {

    public V mvpView;
    protected ApiStores apiStores;
    private CompositeDisposable mCompositeDisposable;

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        apiStores = ApiClient.retrofit().create(ApiStores.class);
    }


    public void detachView() {
        this.mvpView = null;
        onUnSubscribe();
    }


    //RxJava取消注册，以避免内存泄露
    private void onUnSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }


    @SuppressLint("CheckResult")
    public void addSubscription(Observable observable, DisposableObserver observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }

        mCompositeDisposable.add(observer);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }

}

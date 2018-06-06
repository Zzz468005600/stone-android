package zhulei.com.stone.data.model.base;

/**
 * Created by zhulei on 16/8/15.
 */
public abstract class BaseModel {

//    protected ApiStores mApiStores = AppClient.retrofit().create(ApiStores.class);
//    private CompositeSubscription mCompositeSubscription;
//
//    public void onDestroy() {
//        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
//            mCompositeSubscription.unsubscribe();
//        }
//    }
//
//    protected void addSubscription(Observable observable, Subscriber subscriber) {
//        if (mCompositeSubscription == null) {
//            mCompositeSubscription = new CompositeSubscription();
//        }
//        mCompositeSubscription.add(observable
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(subscriber));
//    }

}

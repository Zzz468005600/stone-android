package zhulei.com.stone.data.model.impl;

import java.util.Map;

import zhulei.com.stone.api.AppClient;
import zhulei.com.stone.api.SubscriberCallBack;
import zhulei.com.stone.data.model.base.BaseModel;

/**
 * Created by zhulei on 16/8/29.
 */
public class TestRetrofitModel extends BaseModel {

    public interface OnGetZhiHuListener{
        void OnGetZhiHuSuccess(Map result);
        void OnGetZhiHuFail(String message);
    }

    public void getZhiHu(final OnGetZhiHuListener listener){
        addSubscription(mApiStores.getZhiHu(), new SubscriberCallBack(new AppClient.ApiCallback<Map>() {
            @Override
            public void onSuccess(Map result) {
                listener.OnGetZhiHuSuccess(result);
            }

            @Override
            public void onFail(String message) {
                listener.OnGetZhiHuFail(message);
            }
        }));
    }

}

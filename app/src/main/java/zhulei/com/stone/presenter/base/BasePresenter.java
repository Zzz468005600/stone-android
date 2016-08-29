package zhulei.com.stone.presenter.base;

import zhulei.com.stone.data.model.base.BaseModel;
import zhulei.com.stone.ui.base.IBaseView;

/**
 * Created by zhulei on 16/8/15.
 */
public class BasePresenter<V extends IBaseView, M extends BaseModel> {

    protected V mView;
    protected M mModel;


    public BasePresenter(V view, M model){
        this.mView = view;
        this.mModel = model;
    }

    public void onDestroy(){
        mModel.onDestroy();
    }

}

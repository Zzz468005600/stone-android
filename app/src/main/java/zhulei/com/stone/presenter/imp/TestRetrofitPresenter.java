package zhulei.com.stone.presenter.imp;

import java.util.Map;

import zhulei.com.stone.data.model.entity.ZhiHuImage;
import zhulei.com.stone.data.model.impl.TestRetrofitModel;
import zhulei.com.stone.presenter.ITestRetrofitPresenter;
import zhulei.com.stone.presenter.base.BasePresenter;
import zhulei.com.stone.ui.view.ITestRetrofitView;

public class TestRetrofitPresenter extends BasePresenter<ITestRetrofitView, TestRetrofitModel> implements ITestRetrofitPresenter,
        TestRetrofitModel.OnGetZhiHuListener{

    public TestRetrofitPresenter(ITestRetrofitView view) {
        super(view, new TestRetrofitModel());
    }

    @Override
    public void getZhiHu() {
        mView.showLoading("正在获取");
        mModel.getZhiHu(this);
    }

    @Override
    public void OnGetZhiHuSuccess(Map result) {
        mView.hideLoading();
        mView.onGetZhiHuSuccess(ZhiHuImage.parse(result));
    }

    @Override
    public void OnGetZhiHuFail(String message) {
        mView.hideLoading();
        mView.onGetZhiHuFail(message);
    }
}

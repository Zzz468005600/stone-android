package zhulei.com.stone.ui.view;

import zhulei.com.stone.data.model.entity.ZhiHuImage;
import zhulei.com.stone.ui.base.IBaseView;

/**
 * Created by zhulei on 16/8/29.
 */
public interface ITestRetrofitView extends IBaseView {
    void onGetZhiHuSuccess(ZhiHuImage image);
    void onGetZhiHuFail(String message);
}

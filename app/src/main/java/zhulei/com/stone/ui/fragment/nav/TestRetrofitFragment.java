package zhulei.com.stone.ui.fragment.nav;

import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import zhulei.com.stone.R;
import zhulei.com.stone.data.model.entity.ZhiHuImage;
import zhulei.com.stone.presenter.ITestRetrofitPresenter;
import zhulei.com.stone.ui.base.BaseFragment;
import zhulei.com.stone.ui.view.ITestRetrofitView;

/**
 * Created by zhulei on 16/8/29.
 */
public class TestRetrofitFragment extends BaseFragment<ITestRetrofitPresenter>{

    @BindView(R.id.img)
    ImageView mImg;
    @BindView(R.id.text)
    TextView mText;

    @OnClick(R.id.test_btn)
    void onTestBtnClicked() {

    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        mToolBar.setTitle("测试知乎接口");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    public static TestRetrofitFragment newInstance() {
        return new TestRetrofitFragment();
    }

}

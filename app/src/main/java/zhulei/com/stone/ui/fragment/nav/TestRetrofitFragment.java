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
import zhulei.com.stone.presenter.imp.TestRetrofitPresenter;
import zhulei.com.stone.ui.base.BaseFragment;
import zhulei.com.stone.ui.view.ITestRetrofitView;

/**
 * Created by zhulei on 16/8/29.
 */
public class TestRetrofitFragment extends BaseFragment<ITestRetrofitPresenter> implements ITestRetrofitView {

    @BindView(R.id.img)
    ImageView mImg;
    @BindView(R.id.text)
    TextView mText;

    @OnClick(R.id.test_btn)
    void onTestBtnClicked(){
        mPresenter.getZhiHu();
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        super.initToolBar(toolbar);
        toolbar.setTitle("测试知乎接口");
    }

    @Override
    protected ITestRetrofitPresenter initPresenter() {
        return new TestRetrofitPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    public static TestRetrofitFragment newInstance(){
        TestRetrofitFragment instance = new TestRetrofitFragment();
        return instance;
    }

    @Override
    public void onGetZhiHuSuccess(ZhiHuImage image) {
        if (getActivity() != null){
            mText.setText(image.getText());
            Picasso.with(getContext())
                    .load(image.getImg())
                    .into(mImg);
        }
    }

    @Override
    public void onGetZhiHuFail(String message) {
        if (getActivity() != null){
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}

package zhulei.com.stone.ui.activity;

import zhulei.com.stone.ui.base.BaseActivity;
import zhulei.com.stone.ui.base.BaseFragment;
import zhulei.com.stone.ui.fragment.nav.TestRetrofitFragment;

/**
 * Created by zhulei on 16/8/29.
 */
public class TestRetrofitActivity extends BaseActivity{
    @Override
    protected BaseFragment getFirstFragment() {
        return TestRetrofitFragment.newInstance();
    }
}

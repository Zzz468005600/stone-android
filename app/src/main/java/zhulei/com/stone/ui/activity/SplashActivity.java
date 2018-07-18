package zhulei.com.stone.ui.activity;

import zhulei.com.stone.R;
import zhulei.com.stone.ui.base.BaseActivity;
import zhulei.com.stone.ui.base.BaseFragment;

public class SplashActivity extends BaseActivity {
    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_splash;
    }
}

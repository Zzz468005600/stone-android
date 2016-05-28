package zhulei.com.stone.activity;

import zhulei.com.stone.base.BaseActivity;
import zhulei.com.stone.base.BaseFragment;
import zhulei.com.stone.fragment.LoginFragment;

/**
 * Created by zhulei on 16/5/27.
 */
public class LoginActivity extends BaseActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return new LoginFragment();
    }

}

package zhulei.com.stone.ui.activity.user;

import zhulei.com.stone.ui.base.BaseActivity;
import zhulei.com.stone.ui.base.BaseFragment;
import zhulei.com.stone.ui.fragment.user.LoginFragment;

public class LoginActivity extends BaseActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return LoginFragment.newInstance();
    }

}

package zhulei.com.stone.ui.activity;

import android.content.Intent;

import zhulei.com.stone.ui.base.BaseActivity;
import zhulei.com.stone.ui.base.BaseFragment;
import zhulei.com.stone.ui.fragment.LoginFragment;
import zhulei.com.stone.ui.fragment.VerifyFragment;

/**
 * Created by zhulei on 16/5/27.
 */
public class LoginActivity extends BaseActivity {

    public static final String ARG_LOGIN = "arg_login";
    private int mAction = -1;

    @Override
    protected void handleIntent(Intent intent) {
        mAction = intent.getIntExtra(ARG_LOGIN, -1);
    }

    @Override
    protected BaseFragment getFirstFragment() {
        if (mAction == VerifyFragment.RESET || mAction == VerifyFragment.REGISTER){
            return VerifyFragment.newInstance(mAction);
        }
        return new LoginFragment();
    }

}

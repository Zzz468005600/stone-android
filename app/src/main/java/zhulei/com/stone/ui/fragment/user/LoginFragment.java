package zhulei.com.stone.ui.fragment.user;

import zhulei.com.stone.R;
import zhulei.com.stone.ui.base.BaseFragment;

public class LoginFragment extends BaseFragment {

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

}

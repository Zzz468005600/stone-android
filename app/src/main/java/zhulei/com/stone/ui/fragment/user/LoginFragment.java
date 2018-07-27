package zhulei.com.stone.ui.fragment.user;

import zhulei.com.stone.R;
import zhulei.com.stone.ui.base.BaseFragment;

/**
 * author: zhulei
 * date: 2016/7/26
 * blog: http://www.zhuleiblog.com/
 */
public class LoginFragment extends BaseFragment {

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

}

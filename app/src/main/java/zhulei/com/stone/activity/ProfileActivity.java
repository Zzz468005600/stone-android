package zhulei.com.stone.activity;

import zhulei.com.stone.base.BaseActivity;
import zhulei.com.stone.base.BaseFragment;
import zhulei.com.stone.fragment.ProfileFragment;

/**
 * Created by zhulei on 16/5/30.
 */
public class ProfileActivity extends BaseActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return new ProfileFragment();
    }

}

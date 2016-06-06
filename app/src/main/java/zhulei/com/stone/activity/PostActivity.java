package zhulei.com.stone.activity;

import zhulei.com.stone.base.BaseActivity;
import zhulei.com.stone.base.BaseFragment;
import zhulei.com.stone.fragment.PostFragment;

/**
 * Created by zhulei on 16/5/29.
 */
public class PostActivity extends BaseActivity {
    @Override
    protected BaseFragment getFirstFragment() {
        return PostFragment.newInstance();
    }
}

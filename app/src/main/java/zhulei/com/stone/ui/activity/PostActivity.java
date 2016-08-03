package zhulei.com.stone.ui.activity;

import zhulei.com.stone.ui.base.BaseActivity;
import zhulei.com.stone.ui.base.BaseFragment;
import zhulei.com.stone.ui.fragment.PostFragment;

/**
 * Created by zhulei on 16/5/29.
 */
public class PostActivity extends BaseActivity {
    @Override
    protected BaseFragment getFirstFragment() {
        return PostFragment.newInstance();
    }
}

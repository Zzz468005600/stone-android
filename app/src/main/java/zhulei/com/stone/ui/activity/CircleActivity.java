package zhulei.com.stone.ui.activity;

import zhulei.com.stone.ui.base.BaseActivity;
import zhulei.com.stone.ui.base.BaseFragment;
import zhulei.com.stone.ui.fragment.menu.CircleFragment;

/**
 * Created by zhulei on 16/6/14.
 */
public class CircleActivity extends BaseActivity {
    @Override
    protected BaseFragment getFirstFragment() {
        return CircleFragment.newInstance();
    }
}

package zhulei.com.stone.activity;

import zhulei.com.stone.base.BaseActivity;
import zhulei.com.stone.base.BaseFragment;
import zhulei.com.stone.fragment.menu.CircleFragment;

/**
 * Created by zhulei on 16/6/14.
 */
public class CircleActivity extends BaseActivity {
    @Override
    protected BaseFragment getFirstFragment() {
        return CircleFragment.newInstance();
    }
}

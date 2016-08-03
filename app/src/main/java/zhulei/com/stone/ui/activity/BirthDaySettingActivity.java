package zhulei.com.stone.ui.activity;

import zhulei.com.stone.ui.base.BaseActivity;
import zhulei.com.stone.ui.base.BaseFragment;
import zhulei.com.stone.ui.fragment.BirthDaySettingFragment;

/**
 * Created by zhulei on 16/6/16.
 */
public class BirthDaySettingActivity extends BaseActivity {
    @Override
    protected BaseFragment getFirstFragment() {
        return BirthDaySettingFragment.newInstance();
    }
}

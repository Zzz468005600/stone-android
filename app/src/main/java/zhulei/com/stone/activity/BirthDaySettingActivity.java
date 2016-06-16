package zhulei.com.stone.activity;

import zhulei.com.stone.base.BaseActivity;
import zhulei.com.stone.base.BaseFragment;
import zhulei.com.stone.fragment.BirthDaySettingFragment;

/**
 * Created by zhulei on 16/6/16.
 */
public class BirthDaySettingActivity extends BaseActivity {
    @Override
    protected BaseFragment getFirstFragment() {
        return BirthDaySettingFragment.newInstance();
    }
}

package zhulei.com.stone.ui.activity;

import android.support.v4.app.Fragment;

import zhulei.com.stone.ui.base.BaseActivity;
import zhulei.com.stone.ui.fragment.menu.PersonalPostFragment;

/**
 * Created by zhulei on 16/6/26.
 */
public class PersonalPostActivity extends BaseActivity {
    @Override
    protected Fragment getFirstFragment() {
        return PersonalPostFragment.newInstance();
    }
}
